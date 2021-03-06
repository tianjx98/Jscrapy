package me.tianjx98.Jscrapy.core;

import me.tianjx98.Jscrapy.duplicatefilter.DuplicateFilter;
import me.tianjx98.Jscrapy.http.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

/**
 * 负责管理任务、过滤任务、输出任务的调度器，存储、去重任务
 *
 * @ClassName Scheduler
 * @Author tian
 * @Date 2019/7/21 16:09
 * @Version 1.0
 */
public class Scheduler implements Serializable{
    private static final Logger LOGGER = LoggerFactory.getLogger(Scheduler.class);
    private static final long serialVersionUID = 1L;
    /**
     * 用于去重的类
     */
    private DuplicateFilter filter;
    private int totalRequestNum = 0;
    private int repeatRequestNum = 0;

    /**
     * 任务队列，存放待发送的请求
     * 如果队列是后进先出，则是深度优先爬取
     * 如果队列是先进先出，则是广度优先爬取
     * 默认广度优先爬取
     * TODO：创建一个存储请求的队列接口，可以实现以多种方式来存储待爬取请求，基于内存存储，基于磁盘存储
     */
    private LinkedList<Request> queue = new LinkedList<>();
    /**
     * 默认广度优先爬取
     */
    private boolean bfs;

    protected Scheduler(DuplicateFilter filter, boolean bfs) {
        this.filter = filter;
        this.bfs = bfs;
    }

    /**
     * 向调度器中添加新的请求，如果请求重复自动剔除
     *
     * @param request
     */
    public void addRequest(Request request) {
        // 如果不需要过滤请求就直接跳过查重
        if (request.isDoFilter() && filter.isDuplicate(request)) {
            repeatRequestNum++;
            return;
        }
        if (bfs) queue.addLast(request);
        else queue.addFirst(request);
        totalRequestNum++;
    }

    /**
     * 添加一个新的请求到引擎中
     *
     * @param requests 添加的请求对象
     * @param consumer 添加请求对象前要向请求对象执行的操作
     * @return 添加的请求对象
     */
    public synchronized void addRequest(List<Request> requests, Consumer<Request> consumer) {
        if (requests == null) return;
        for (Request request : requests) {
            if (consumer != null) consumer.accept(request);
            addRequest(request);
        }
    }

    public DuplicateFilter getFilter() {
        return filter;
    }

    /**
     * 获取下一个请求返回，并从调度器中删除该请求
     *
     * @return 请求类对象，如果调度器中请求为空，返回null
     */
    public synchronized Request nextRequest() {
        return queue.pollFirst();
    }

    public synchronized boolean isEmpty() {
        return queue.isEmpty();
    }

    public synchronized int size() {
        return queue.size();
    }

    public LinkedList<Request> getQueue() {
        return queue;
    }

    public int getTotalRequestNum() {
        return totalRequestNum;
    }

    public int getRepeatRequestNum() {
        return repeatRequestNum;
    }
}
