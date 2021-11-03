package me.tianjx98.jscrapy.http;

import java.util.Map;
import java.util.function.Function;

import me.tianjx98.jscrapy.core.Spider;
import reactor.core.publisher.Flux;

/**
 * @author tianjx98
 * @date 2021/11/2 14:52
 */
public interface Request {

    String getDomain();

    String getUrl();

    boolean isDoFilter();

    Map<String, String> getHeaders();

    void addHeader(String key, String value);

    Spider getSpider();

    Function<Response, Flux<Request>> getCallback();

    okhttp3.Request buildRequest();

}
