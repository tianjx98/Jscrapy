package me.tianjx98.jscrapy.core.impl2;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedDeque;

import lombok.extern.log4j.Log4j2;
import me.tianjx98.jscrapy.core.Scheduler;
import me.tianjx98.jscrapy.duplicatefilter.DuplicateFilter;
import me.tianjx98.jscrapy.duplicatefilter.impl.BloomDuplicateFilter;
import me.tianjx98.jscrapy.http.Request;
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
        requests.filter(request -> request.isDoFilter() && duplicateFilter.isDuplicate(request)).subscribe(queue::add);
    }

    @Override
    public Request nextRequest() {
        return queue.poll();
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public int size() {
        return queue.size();
    }
}