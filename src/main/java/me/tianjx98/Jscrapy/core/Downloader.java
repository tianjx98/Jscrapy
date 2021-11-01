package me.tianjx98.Jscrapy.core;

import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

import me.tianjx98.Jscrapy.http.Request;

/**
 * @author 18872653103
 * @date 2021/7/26 19:26
 */
public interface Downloader {

    void download(Request request, FutureCallback<HttpResponse> callback);

    long totalRequest();

    int currentRequest();

    boolean isEmpty();

    void close();
}
