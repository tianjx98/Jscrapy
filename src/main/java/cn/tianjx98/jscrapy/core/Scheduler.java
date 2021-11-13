package cn.tianjx98.jscrapy.core;

import cn.tianjx98.jscrapy.http.Request;
import reactor.core.publisher.Flux;

/**
 * 请求调度器<br>
 * 存放爬虫类产生的{@link Request}对象, 放入请求时会进行以下操作<br>
 * 1.调用{@link Request#validateUrl()}方法校验url是否正确, 错误的url会被丢弃
 * 2.判断url是否重复
 *
 * <br>
 * 爬虫引擎启动时或每当有请求完成时会从调度器中取请求对象
 *
 * @author tianjx98
 * @date 2021/7/26 19:25
 */
public interface Scheduler {

    /**
     * 添加请求
     *
     * @param requests 请求
     */
    void addRequests(Flux<Request> requests);

    /**
     * 添加请求
     *
     * @param requests 请求
     */
    void addRequest(Request requests);

    /**
     * 取队列头的第一个请求, 不删除
     *
     * @return {@link Request}
     */
    Request pollRequest();

    /**
     * 取队列头的第一个请求, 并删除
     *
     * @return {@link Request}
     */
    Request peekRequest();

    /**
     * 调度器是否为空
     *
     * @return boolean
     */
    boolean isEmpty();

    /**
     * 调度器当前请求个数
     *
     * @return int 请求个数
     */
    int size();
}
