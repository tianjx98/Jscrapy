package me.tianjx98.jscrapy.core.impl2;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import lombok.extern.log4j.Log4j2;
import me.tianjx98.jscrapy.core.Downloader;
import me.tianjx98.jscrapy.http.Request;
import me.tianjx98.jscrapy.http.client.HttpClient;
import me.tianjx98.jscrapy.http.client.impl.DefaultHttpClient;
import okhttp3.Callback;

/**
 * @author tianjx98
 * @date 2021/11/1 17:00
 */
@Log4j2
public class DefaultDownloader implements Downloader {

    /**
     * 请求的最大并发数, 默认最多只能同时发送16个请求
     */
    private int concurrentRequests = 16;
    /**
     * 每一个域名下的最大并发数, 默认为8
     */
    private int concurrentRequestsPerDomain = 8;

    /**
     * TODO 随机延迟(ms)
     */
    private int randomDownloadDelay;

    private HttpClient client;

    /**
     * 已经发送的请求
     */
    private final Map<String, Set<Request>> crawling = new ConcurrentHashMap<>();

    /**
     * 记录发送的请求总数，每发送一个请求，count加1
     */
    private final AtomicLong totalRequest = new AtomicLong(0);

    /**
     * 当前已经发送的请求数量，每发送一个请求，size加1，请求完成后size减1
     */
    private final AtomicInteger currentRequest = new AtomicInteger(0);

    public DefaultDownloader() {
        this.client = new DefaultHttpClient();
    }

    @Override
    public void download(Request request, Callback callback) {
        final String domain = request.getDomain();
        Set<Request> requests = crawling.computeIfAbsent(domain, k -> new HashSet<>());
        requests.add(request);
        client.executeAsync(request, callback);
        currentRequest.incrementAndGet();
        log.info("当前 {} 个请求正在下载", currentRequest.get());
    }

    @Override
    public boolean needBlock(Request request) {
        if (currentRequest.get() > concurrentRequests) {
            return true;
        }
        final Set<Request> requests = crawling.get(request.getDomain());
        if (requests == null) {
            return false;
        }
        return requests.size() > concurrentRequestsPerDomain;
    }

    @Override
    public void remove(Request request) {
        final Set<Request> domainRequests = crawling.get(request.getDomain());
        domainRequests.remove(request);
        currentRequest.decrementAndGet();
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
