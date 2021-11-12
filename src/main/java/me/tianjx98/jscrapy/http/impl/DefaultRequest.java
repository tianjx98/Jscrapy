package me.tianjx98.jscrapy.http.impl;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.function.Function;

import lombok.extern.log4j.Log4j2;
import me.tianjx98.jscrapy.core.Element;
import me.tianjx98.jscrapy.core.Spider;
import me.tianjx98.jscrapy.http.Request;
import me.tianjx98.jscrapy.http.RequestBuilder;
import me.tianjx98.jscrapy.http.Response;
import reactor.core.publisher.Flux;

/**
 * @author tianjx98
 */
@Log4j2
public class DefaultRequest implements Request {

    private final Spider spider;
    private final String url;
    private URL urlObject;
    private RequestBuilder requestBuilder;
    private boolean doFilter;
    private final Function<Response, Flux<Element>> callback;

    private Map<String, String> headers;
    private Object requestBody;

    public DefaultRequest(Spider spider, String url, Function<Response, Flux<Element>> callback) {
        this.spider = spider;
        this.url = url;
        this.callback = callback;
    }

    @Override
    public boolean validateUrl() {
        try {
            this.urlObject = new URL(url);
            return true;
        } catch (MalformedURLException e) {
            log.error("URL格式错误, 取消发送请求", e);
            return false;
        }
    }

    @Override
    public String getDomain() {
        return urlObject.getHost();
    }

    @Override
    public String getUrl() {
        return urlObject.toString();
    }

    @Override
    public boolean isDoFilter() {
        return doFilter;
    }

    @Override
    public Map<String, String> getHeaders() {
        return headers;
    }

    @Override
    public void addHeader(String key, String value) {
        headers.put(key, value);
    }

    @Override
    public Spider getSpider() {
        return spider;
    }

    @Override
    public Function<Response, Flux<Element>> getCallback() {
        return callback;
    }

    @Override
    public okhttp3.Request buildRequest() {
        final okhttp3.Request.Builder builder = new okhttp3.Request.Builder();
        if (requestBuilder == null) {
            return builder.build();
        }
        return requestBuilder.apply(builder, this).build();
    }

    public void setRequestBuilder(RequestBuilder requestBuilder) {
        this.requestBuilder = requestBuilder;
    }

    @Override
    public String toString() {
        return "DefaultRequest{" + "url=" + url + '}';
    }
}
