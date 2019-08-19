package me.tianjx98.Jscrapy.core;

import com.typesafe.config.Config;
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
    private static final Config SETTINGS = Setting.SETTINGS;

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

    private int size = 0;

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
        requests.remove(request);
        this.size--;
    }

    void close() {
        client.close();
    }

    boolean isEmpty() {
        return size == 0;
    }

    public int getCount() {
        return count;
    }
}