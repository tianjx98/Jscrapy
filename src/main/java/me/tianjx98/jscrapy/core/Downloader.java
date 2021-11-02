package me.tianjx98.jscrapy.core;

import me.tianjx98.jscrapy.http.Request;
import okhttp3.Callback;

/**
 * @author 18872653103
 * @date 2021/7/26 19:26
 */
public interface Downloader {

    void download(Request request, Callback callback);

    boolean needBlock();

    void remove(Request request);

    long totalRequest();

    int currentRequest();

    boolean isEmpty();

    void close();
}
