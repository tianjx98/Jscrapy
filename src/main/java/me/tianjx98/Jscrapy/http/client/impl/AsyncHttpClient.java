package me.tianjx98.Jscrapy.http.client.impl;


import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.Response;
import me.tianjx98.Jscrapy.http.client.HttpClient;
import me.tianjx98.Jscrapy.utils.Out;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.conn.NoopIOSessionStrategy;
import org.apache.http.nio.conn.SchemeIOSessionStrategy;
import org.apache.http.nio.conn.ssl.SSLIOSessionStrategy;
import org.apache.http.nio.reactor.IOReactorException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * 异步请求客户端，发送请求后不会阻塞，而是等收到响应后自动调用回调函数来处理响应
 */
public class AsyncHttpClient implements HttpClient {
    private static Logger LOGGER = LoggerFactory.getLogger(AsyncHttpClient.class);
    private BasicCookieStore cookies = new BasicCookieStore();
    private CloseableHttpAsyncClient client = null;

    /**
     * 默认请求客户端，可以自动管理cookies
     */
    public AsyncHttpClient() {
        this(null, 10000, Runtime.getRuntime().availableProcessors());
    }

    /**
     * 给请求客户端添加代理和默认请求头，可以自动管理cookies
     *
     * @param host           代理，会通过代理发送请求，如果是需要翻墙才能访问，需要添加代理，可以为空
     * @param maxThreadNum   下载请求的最大线程数
     * @param connTimeout    连接超时时间
     */
    public AsyncHttpClient(HttpHost host, int connTimeout, int maxThreadNum) {
        try {
            LOGGER.info("HOST: " + host);
            creatClient(host, connTimeout, maxThreadNum);
            LOGGER.trace("创建异步请求客户端");
        } catch (IOReactorException | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }
    }


    /**
     * 根据url发送异步请求
     *
     * @param url 请求url
     * @return 如果请求成功返回响应内容，否则返回null
     */
    public Response get(String url) {
        Objects.requireNonNull(url, "url");
        HttpGet httpGet = new HttpGet(url);
        Future<HttpResponse> future = client.execute(httpGet, null);
        try {
            return new Response(null, future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void execute(Request request, FutureCallback<HttpResponse> callback) {
        LOGGER.trace(request.toString());
        client.execute(request.generateRequest(), callback);
    }

    /**
     * 发送异步POST请求
     *
     * @param url 请求url
     * @return
     */
    public Response post(String url) {
        return post(url, null, null);
    }

    /**
     * 发送异步POST请求
     *
     * @param url       请求url
     * @param paramsMap 请求参数
     * @return
     */
    protected Response post(String url, Map<String, String> paramsMap) {
        return post(url, paramsMap, null);
    }

    /**
     * 发送异步POST请求
     *
     * @param url       请求url
     * @param paramsMap 请求参数
     * @param headers   请求头
     * @return
     */
    protected Response post(String url, Map<String, String> paramsMap, Header[] headers) {
        Objects.requireNonNull(url, "url");
        HttpPost httpPost = new HttpPost(url);
        // 设置请求头
        if (headers != null && headers.length > 0) {
            httpPost.setHeaders(headers);
        }
        // 设置请求参数
        if (paramsMap != null && paramsMap.size() > 0) {
            UrlEncodedFormEntity entity = null;
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            for (Map.Entry<String, String> entry : paramsMap.entrySet()) {
                params.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            try {
                entity = new UrlEncodedFormEntity(params, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            httpPost.setEntity(entity);
        }
        Future<HttpResponse> future = client.execute(httpPost, null);
        try {
            return new Response(null, future.get());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 创建请求客户端
     *
     * @param host
     * @param connTimeout
     * @param maxThreadNum
     * @throws IOReactorException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private void creatClient(HttpHost host, int connTimeout, int maxThreadNum) throws IOReactorException, NoSuchAlgorithmException, KeyManagementException {
        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<SchemeIOSessionStrategy> sessionStrategyRegistry = RegistryBuilder
                .<SchemeIOSessionStrategy>create()
                .register("http", NoopIOSessionStrategy.INSTANCE)
                .register("https", new SSLIOSessionStrategy(SyncHttpClient.createIgnoreVerifySSL()))
                .build();

        // 配置io线程
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                .setIoThreadCount(maxThreadNum)
                //.setConnectTimeout(56)
                .setSoTimeout(connTimeout)// 设置超时时间，单位为ms
                .build();
        PoolingNHttpClientConnectionManager conMgr = new PoolingNHttpClientConnectionManager(new DefaultConnectingIOReactor(ioReactorConfig), sessionStrategyRegistry);

        readCookiesFromDisk();

        HttpAsyncClientBuilder builder = HttpAsyncClients
                .custom()
                .setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build())
                .setConnectionManager(conMgr)
                .setDefaultIOReactorConfig(ioReactorConfig)
                .setDefaultCookieStore(cookies);
        if (host != null) {
            builder.setProxy(host);//设置代理(Shadowsocks)
        }
        client = builder.build();
        client.start();
    }

    private void readCookiesFromDisk() {
        File file = new File("cookies");
        if (!file.exists()) {
            return;
        }
        String cookiesString = Out.read(file);
        String[] cs = cookiesString.split(";");
        for (String s : cs) {
            String[] nameValue = s.split("=");
            if (nameValue.length != 2) continue;
            this.cookies.addCookie(new BasicClientCookie(nameValue[0], nameValue[1]));
        }
    }

    /**
     * 关闭客户端连接
     */
    public void close() {
        try {
            if (client != null) {
                client.close();
                LOGGER.trace("关闭异步请求客户端");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
