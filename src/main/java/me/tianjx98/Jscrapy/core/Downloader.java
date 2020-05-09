package me.tianjx98.Jscrapy.core;

import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.client.HttpClient;
import me.tianjx98.Jscrapy.utils.Setting;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * 主要用于下载各种请求
 * 包含下载处理器，下载器中间件
 *
 * @ClassName Downloader
 * @Author tian
 * @Date 2019/7/22 13:47
 * @Version 1.0
 */
public class Downloader {
    private static final Logger LOGGER = LoggerFactory.getLogger(Downloader.class);
    private static final Setting SETTINGS = Setting.SETTINGS;

    //private final Timer timer = new Timer();

    /**
     * 请求的最大并发数, 默认最多只能同时发送16个请求
     */
    private int concurrentRequests;
    /**
     * 每一个域名下的最大并发数, 默认为8
     */
    private int concurrentRequestsPerDomain;
    /**
     * TODO
     * 随机延迟
     */
    private int randomDownloadDelay;

    private HttpClient client;

    private Map<String, Set<Request>> crawling = new HashMap<>();

    /**
     * 当前已经发送的请求数量，每发送一个请求，size加1，请求完成后size减1
     */
    private int size = 0;

    /**
     * 记录发送的请求总数，每发送一个请求，count加1
     */
    private int count = 0;

    protected Downloader(int concurrentRequests, int concurrentRequestsPerDomain, int randomDownloadDelay, HttpClient client) {
        this.concurrentRequests = concurrentRequests;
        this.concurrentRequestsPerDomain = concurrentRequestsPerDomain;
        this.randomDownloadDelay = randomDownloadDelay;
        this.client = client;
    }

    /**
     * 检查是否需要阻塞
     *
     * @return 如果达到最大并发数或者同一请求下的最大并发数上限就返回true
     */
    boolean needBlock() {
        //Set<Request> requests = crawling.get(request.getDomain());
        //return requests != null && requests.size() >= concurrentRequestsPerDomain;
        return size >= concurrentRequests;
    }

    /**
     * 下载请求
     *
     * @param request  请求对象
     * @param callback 下载完成后调用的回调函数
     */
    void download(Request request, FutureCallback<HttpResponse> callback) {
        // 将请求添加到请求队列
        this.size++;
        this.count++;
        String domain = request.getDomain();
        Set<Request> requests = crawling.get(domain);
        if (requests == null) {
            requests = new HashSet<>();
            crawling.put(domain, requests);
        }
        requests.add(request);
        LOGGER.debug("发送请求：" + request);
        // 发送请求
        client.execute(request, callback);
    }

    /**
     * 移除已经完成的请求
     *
     * @param request 需要移除的请求对象
     */
    void remove(Request request) {
        Set<Request> requests = crawling.get(request.getDomain());
        LOGGER.debug("请求完成：" + request);
        requests.remove(request);
        size--;
    }

    public Map<String, Set<Request>> getCrawling() {
        return crawling;
    }

    /**
     * 关闭下载器，如果下载器还有任务没完成，就等待任务完成
     */
    public void close() {
        client.close();
    }

    /**
     * 判断下载器正在下载的请求是否为空
     *
     * @return 如果正在下载的请求数量为0就返回true, 否则返回false
     */
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * 返回已经发出的请求总数
     *
     * @return 已经发出的请求数
     */
    public int getCount() {
        return count;
    }

    /**
     * 返回正在请求的请求数量
     *
     * @return 在请求的请求数量
     */
    public int getSize() {
        return size;
    }
}
