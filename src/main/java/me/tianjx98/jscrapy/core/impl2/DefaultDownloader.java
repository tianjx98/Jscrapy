package me.tianjx98.jscrapy.core.impl2;

import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import me.tianjx98.jscrapy.http.Request;
import org.jetbrains.annotations.NotNull;

import me.tianjx98.jscrapy.core.Downloader;
import me.tianjx98.jscrapy.http.client.HttpClient;
import me.tianjx98.jscrapy.http.client.impl.DefaultHttpClient;
import me.tianjx98.jscrapy.http.impl.DefaultResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

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

    public DefaultDownloader() {
        this.client = new DefaultHttpClient();
    }

    @Override
    public void download(Request request, Callback callback) {
        client.execute(request, callback);
    }

    @Override
    public boolean needBlock() {
        // todo 实现并发控制
        return false;
    }

    @Override
    public void remove(Request request) {

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
