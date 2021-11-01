package me.tianjx98.Jscrapy.core;

import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.Response;
import reactor.core.publisher.Flux;

/**
 * @author 18872653103
 * @date 2021/7/26 19:25
 */
public interface Spider {

    String getName();

    Flux<String> startUrls();

    Flux<Request> startRequests();

    Flux<Request> parse(Response response);

}
