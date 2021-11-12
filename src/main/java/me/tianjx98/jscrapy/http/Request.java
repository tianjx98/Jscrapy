package me.tianjx98.jscrapy.http;

import java.util.Map;
import java.util.function.Function;

import lombok.NonNull;
import me.tianjx98.jscrapy.core.Element;
import me.tianjx98.jscrapy.core.Spider;
import reactor.core.publisher.Flux;

/**
 * @author tianjx98
 * @date 2021/11/2 14:52
 */
public interface Request extends Element {

    boolean validateUrl();

    String getDomain();

    String getUrl();

    boolean isDoFilter();

    Map<String, String> getHeaders();

    void addHeader(String key, String value);

    Spider getSpider();

    Function<Response, Flux<Element>> getCallback();

    okhttp3.Request buildRequest();

}
