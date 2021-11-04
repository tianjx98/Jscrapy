package me.tianjx98.jscrapy.core;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import me.tianjx98.jscrapy.http.Request;
import me.tianjx98.jscrapy.http.Response;
import reactor.core.publisher.Flux;

/**
 * @author 18872653103
 * @date 2021/7/26 19:25
 */
public interface Spider {

    String getName();

    Flux<String> startUrls();

    Flux<Request> startRequests();

    default Function<Response, Flux<? extends Element>> startCallback() {
        return this::parse;
    }

    Flux<? extends Element> parse(Response response);

    default Map<String, String> getDefaultHeaders() {
        return Collections.emptyMap();
    };
}
