package me.tianjx98.jscrapy.core;

import me.tianjx98.jscrapy.http.Request;
import reactor.core.publisher.Flux;

/**
 * @author tianjx98
 * @date 2021/7/26 19:25
 */
public interface Scheduler {

    void addRequests(Flux<Request> requests);

    Request pollRequest();

    Request peekRequest();

    boolean isEmpty();

    int size();
}
