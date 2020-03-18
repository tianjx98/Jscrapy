package me.tianjx98.Jscrapy.http;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigValue;
import me.tianjx98.Jscrapy.core.Spider;
import me.tianjx98.Jscrapy.utils.Setting;
import org.apache.http.Header;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;
import java.util.function.Function;

public class Request implements Serializable {
    private static Logger logger = LoggerFactory.getLogger(Request.class);

    /**
     * 发出该请求的爬虫
     */
    private Spider spider;
    /**
     * 请求url
     */
    private URL url;
    /**
     * 请求头
     */
    private Map<String, String> headers;
    /**
     * 请求体，如果请求体不为空，则发送POST请求，否则发送GET请求
     */
    private List<BasicNameValuePair> requestBodies;
    /**
     * 请求成功时调用此回调函数
     */
    private Function<Response, List<Request>> callback;
    /**
     * 请求失败时调用此回调函数
     */
    private Function<Response, Object> errback;
    /**
     * 发出请求时是否过滤，如果为true，则不会发送重复请求
     */
    private boolean doFilter;


    private Map<Object, Object> data;

    private Request() {
    }

    private Request(URL url) {
        this();
        this.url = url;
    }

    private Request(Spider spider, URL url, Map<String, String> headers, List<BasicNameValuePair> requestBodies, Function<Response, List<Request>> callback, Function<Response, Object> errback, boolean doFilter, Map<Object, Object> data) {
        this();
        this.spider = spider;
        this.url = url;
        this.headers = headers;
        this.requestBodies = requestBodies;
        this.callback = callback;
        this.errback = errback;
        this.doFilter = doFilter;
        this.data = data;
    }

    public static Builder builder(String url, Spider spider) {
        Objects.requireNonNull(url, "url");
        Objects.requireNonNull(spider, "spider");
        try {
            URL url1 = new URL(url);
            return new Builder(url1, spider);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Builder builder(URL url, Spider spider) {
        return new Builder(url, spider);
    }

    @Override
    public int hashCode() {
        return url.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (!(obj instanceof Request)) return false;
        Request o = (Request) obj;
        return url.equals(o.url);
    }

    @Override
    public String toString() {
        return "[url=" + url + ", headers=" + headers + ", bodies=" + requestBodies + "]";
    }

    /**
     * 如果请求成功，就执行此回调函数
     *
     * @param response
     * @return
     */
    public List<Request> callback(Response response) {
        if (callback == null) return null;
        try {
            return callback.apply(response);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 如果请求失败，就执行此回调函数
     *
     * @param response
     * @return
     */
    public Object errback(Response response) {
        if (errback == null) return null;
        return errback.apply(response);
    }

    /**
     * 获取请求url
     *
     * @return 请求URL对象
     */
    public URL getUrl() {
        return url;
    }

    /**
     * 获取请求域名
     *
     * @return 域名字符串
     */
    public String getDomain() {
        return url.getHost();
    }

    /**
     * 根据请求对象生成GET或POST请求对象
     * 如果生成请求过程中产生异常，将返回null
     *
     * @return 请求对象
     */
    public HttpRequestBase getRequest() {
        Header[] headers = new Header[this.headers.size()];
        int i = 0;
        for (Map.Entry<String, String> entry : this.headers.entrySet()) {
            headers[i++] = new BasicHeader(entry.getKey(), entry.getValue());
        }
        if (requestBodies.size() == 0) {
            try {
                HttpGet httpGet = new HttpGet(url.toURI());
                httpGet.setHeaders(headers);
                return httpGet;
            } catch (URISyntaxException e) {
                e.printStackTrace();
            }
        } else {
            try {
                HttpPost httpPost = new HttpPost(url.toURI());
                httpPost.setHeaders(headers);
                BasicNameValuePair[] pairs = new BasicNameValuePair[requestBodies.size()];
                httpPost.setEntity(new UrlEncodedFormEntity(requestBodies, "UTF-8"));
                return httpPost;
            } catch (URISyntaxException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 获取所有的请求头
     *
     * @return
     */
    public Map<String, String> getHeaders() {
        return headers;
    }

    /**
     * 获取所有的请求体
     *
     * @return
     */
    public List<BasicNameValuePair> getRequestBodies() {
        return requestBodies;
    }

    /**
     * 是否过滤请求，如果过滤，之前访问过的请求就不会再次访问
     *
     * @return
     */
    public boolean isDoFilter() {
        return doFilter;
    }

    public void setDoFilter(boolean doFilter) {
        this.doFilter = doFilter;
    }

    public Spider getSpider() {
        return spider;
    }

    public void setCallback(Function<Response, List<Request>> callback) {
        this.callback = callback;
    }

    public void setSpider(Spider spider) {
        this.spider = spider;
    }

    public Map<Object, Object> getDataMap() {
        return data;
    }

    public Object getData(String key) {
        return data.get(key);
    }

    public void addData(Object key, Object value) {
        data.put(key, value);
    }

    public List<Request> asList() {
        ArrayList<Request> list = new ArrayList<>();
        list.add(this);
        return list;
    }

    public static class Builder {
        private Spider spider;
        private URL url;

        private Map<String, String> requestHeaders = new HashMap<>();
        private List<BasicNameValuePair> requestBodies = new LinkedList<>();
        private Function<Response, List<Request>> callback;
        private Function<Response, Object> errback;
        private boolean doFilter = true;
        private Map<Object, Object> data = new HashMap<>();

        private Builder(URL url, Spider spider) {
            this.url = url;
            this.spider = spider;
            // 从配置文件中读取默认的请求头
            Config defaultHeaders = Setting.SETTINGS.getConfig("defaultHeaders");
            for (Map.Entry<String, ConfigValue> entry : defaultHeaders.entrySet()) {
                requestHeaders.put(entry.getKey(), entry.getValue().unwrapped().toString());
            }
            // 再读取Spider里面的默认请求头, 会覆盖配置文件中的
            requestHeaders.putAll(spider.getDefaultHeaders());
        }

        public Builder addHeader(String name, String value) {
            requestHeaders.put(name, value);
            return this;
        }

        public Builder addHeaders(Map<String, String> headers) {
            requestHeaders.putAll(headers);
            return this;
        }

        public Builder addBody(String name, String value) {
            requestBodies.add(new BasicNameValuePair(name, value));
            return this;
        }

        public Builder addBodies(Map<String, String> bodiesMap) {
            for (Map.Entry<String, String> entry : bodiesMap.entrySet()) {
                requestBodies.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
            }
            return this;
        }

        public Builder addData(Object key, Object value) {
            data.put(key, value);
            return this;
        }

        public Builder callback(Function<Response, List<Request>> callback) {
            this.callback = callback;
            return this;
        }

        public Builder errback(Function<Response, Object> errback) {
            this.errback = errback;
            return this;
        }

        public Builder doFilter(boolean doFilter) {
            this.doFilter = doFilter;
            return this;
        }

        public Request build() {
            if (url == null) return null;
            Objects.requireNonNull(url, "url");
            //Objects.requireNonNull(callback, "callback");
            return new Request(spider, url, requestHeaders, requestBodies, callback, errback, doFilter, data);
        }
    }

}