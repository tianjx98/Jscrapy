package me.tianjx98.jscrapy.core;

import me.tianjx98.jscrapy.http.Request;
import okhttp3.Callback;

/**
 * @author 18872653103
 * @date 2021/7/26 19:26
 */
public interface Downloader {

    /**
     * 下载请求
     *
     * @param request  请求对象
     * @param callback 回调方法
     */
    void download(Request request, Callback callback);

    /**
     * 是否需要阻塞, 当下载器总请求数达到上限, 或某一域名下的请求达到上限时会停止发送请求
     *
     * @param request 请求
     * @return boolean
     */
    boolean needBlock(Request request);

    /**
     * 从下载器删除已经完成的请求
     *
     * @param request 请求
     */
    void remove(Request request);

    /**
     * 下载器下载的请求总数
     *
     * @return long 请求总数
     */
    long totalRequest();

    /**
     * 当前请求数
     *
     * @return int 下载当前已经发送但未收到响应的请求数
     */
    int currentRequest();

    /**
     * 下载器是否为空
     *
     * @return boolean
     */
    boolean isEmpty();

    /**
     * 关闭下载器,爬虫引擎关闭是会调用此方法
     */
    void close();
}
