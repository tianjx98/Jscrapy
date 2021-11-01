package me.tianjx98.Jscrapy.core;

import me.tianjx98.Jscrapy.http.Request;
import reactor.core.publisher.Flux;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * @author 18872653103
 * @date 2021/7/26 19:25
 */
public interface Spider {

    String getName();

    Flux<Request> startUrls();

    Flux<Request> startRequests();

    default Flux<Map<String, String>> defaultHeaders() {
        return null;
    }

}
