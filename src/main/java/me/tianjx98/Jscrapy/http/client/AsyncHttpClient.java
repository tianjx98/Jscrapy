package me.tianjx98.Jscrapy.http.client;


import com.typesafe.config.Config;
import me.tianjx98.Jscrapy.http.Response;
import me.tianjx98.Jscrapy.utils.Setting;
import org.apache.http.Header;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
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

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class AsyncHttpClient {
    private static Logger logger = LoggerFactory.getLogger(AsyncHttpClient.class);
    private CloseableHttpAsyncClient client = null;

    public AsyncHttpClient(){
        try {
            creatClient();
            logger.trace("创建异步请求客户端");
        } catch (IOReactorException | KeyStoreException | NoSuchAlgorithmException | KeyManagementException e) {
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

    public void execute(HttpRequestBase request, FutureCallback<HttpResponse> callback) {
        logger.trace(request.toString());
        client.execute(request, callback);
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
     * @throws IOReactorException
     * @throws KeyStoreException
     * @throws NoSuchAlgorithmException
     * @throws KeyManagementException
     */
    private void creatClient() throws IOReactorException, KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        Config proxy = Setting.settings.getObject("proxy").toConfig();
        HttpHost host = new HttpHost(proxy.getString("host"), proxy.getInt("port"));

        // 设置协议http和https对应的处理socket链接工厂的对象
        Registry<SchemeIOSessionStrategy> sessionStrategyRegistry = RegistryBuilder
                .<SchemeIOSessionStrategy>create()
                .register("http", NoopIOSessionStrategy.INSTANCE)
                .register("https", new SSLIOSessionStrategy(SyncHttpClient.createIgnoreVerifySSL()))
                .build();

        // 配置io线程
        IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                .setIoThreadCount(Runtime.getRuntime().availableProcessors())
                .build();
        PoolingNHttpClientConnectionManager conMgr = new PoolingNHttpClientConnectionManager(new DefaultConnectingIOReactor(ioReactorConfig), sessionStrategyRegistry);
        client = HttpAsyncClients
                .custom()
                .setConnectionManager(conMgr)
                .setProxy(host) //设置Shadowsocks代理
                .setDefaultIOReactorConfig(ioReactorConfig)
                .setDefaultCookieStore(new BasicCookieStore())
                .build();
        client.start();

    }

    /**
     * 关闭客户端连接
     */
    public void close() {
        try {
            if (client != null) {
                client.close();
                logger.trace("关闭异步请求客户端");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
