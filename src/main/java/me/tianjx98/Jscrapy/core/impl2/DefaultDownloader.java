package me.tianjx98.Jscrapy.core.impl2;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

import me.tianjx98.Jscrapy.core.Downloader;
import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.client.HttpClient;

/**
 * @author tianjx98
 * @date 2021/11/1 17:00
 */
public class DefaultDownloader implements Downloader {

    /**
     * 请求的最大并发数, 默认最多只能同时发送16个请求
     */
    private int concurrentRequests = 16;
    /**
     * 每一个域名下的最大并发数, 默认为8
     */
    private int concurrentRequestsPerDomain;

    /**
     * TODO 随机延迟(ms)
     */
    private int randomDownloadDelay;

    private HttpClient client;

    /**
     * 已经发送的请求
     */
    private Map<String, Set<Request>> crawling = new ConcurrentHashMap<>();

    /**
     * 记录发送的请求总数，每发送一个请求，count加1
     */
    private final AtomicLong totalRequest = new AtomicLong(0);

    /**
     * 当前已经发送的请求数量，每发送一个请求，size加1，请求完成后size减1
     */
    private final AtomicInteger currentRequest = new AtomicInteger(0);


    @Override
    public void download(Request request, FutureCallback<HttpResponse> callback) {
        System.out.println();
    }

    @Override
    public long totalRequest() {
        return totalRequest.get();
    }

    @Override
    public int currentRequest() {
        return currentRequest.get();
    }

    @Override
    public boolean isEmpty() {
        return currentRequest.get() == 0;
    }

    @Override
    public void close() {

    }
}
