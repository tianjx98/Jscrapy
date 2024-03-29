package cn.tianjx98.jscrapy.core.impl;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import cn.tianjx98.jscrapy.core.Scheduler;
import cn.tianjx98.jscrapy.duplicatefilter.DuplicateFilter;
import cn.tianjx98.jscrapy.duplicatefilter.impl.BloomDuplicateFilter;
import cn.tianjx98.jscrapy.http.Request;
import lombok.extern.log4j.Log4j2;
import reactor.core.publisher.Flux;

/**
 * @author tianjx98
 * @date 2021/11/2 16:26
 */
@Log4j2
public class DefaultScheduler implements Scheduler {
    private final DuplicateFilter duplicateFilter;
    private final Queue<Request> queue = new ConcurrentLinkedDeque<>();

    public DefaultScheduler() {
        this.duplicateFilter = new BloomDuplicateFilter();
    }

    @Override
    public void addRequests(Flux<Request> requests) {
        requests.filter(Request::validateUrl).filter(request -> !(request.isDoFilter() && duplicateFilter.isDuplicate(request)))
                .subscribe(queue::add);
    }

    @Override
    public void addRequest(Request request) {
        if (request.isDoFilter() && duplicateFilter.isDuplicate(request)) {
            return;
        }
        if (request.validateUrl()) {
            queue.add(request);
        }
    }

    @Override
    public Request pollRequest() {
        return queue.poll();
    }

    @Override
    public Request peekRequest() {
        return queue.peek();
    }

    @Override
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    public int size() {
        return queue.size();
    }
}
