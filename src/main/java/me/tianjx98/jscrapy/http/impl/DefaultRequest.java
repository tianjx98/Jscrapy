package me.tianjx98.jscrapy.http.impl;

import java.net.URL;
import java.util.Map;
import java.util.function.Function;

import lombok.extern.log4j.Log4j2;
import me.tianjx98.jscrapy.core.Element;
import me.tianjx98.jscrapy.core.Spider;
import me.tianjx98.jscrapy.http.Request;
import me.tianjx98.jscrapy.http.Response;
import reactor.core.publisher.Flux;

/**
 * @author tianjx98
 */
@Log4j2
public class DefaultRequest implements Request {

    private final Spider spider;
    private final URL url;
    private boolean doFilter;
    private final Function<Response, Flux<? extends Element>> callback;

    private Map<String, String> headers;
    private Object requestBody;

    public DefaultRequest(Spider spider, URL url, Function<Response, Flux<? extends Element>> callback) {
        this.spider = spider;
        this.url = url;
        this.callback = callback;
    }

    @Override
    public String getDomain() {
        return url.getHost();
    }

    @Override
    public String getUrl() {
        return url.toString();
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
    public Function<Response, Flux<? extends Element>> getCallback() {
        return callback;
    }

    @Override
    public okhttp3.Request buildRequest() {

        return new okhttp3.Request.Builder().url(url).build();
    }

    @Override
    public String toString() {
        return "DefaultRequest{" + "url=" + url + '}';
    }
}
