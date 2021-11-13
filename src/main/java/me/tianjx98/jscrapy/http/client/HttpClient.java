package me.tianjx98.jscrapy.http.client;

import me.tianjx98.jscrapy.http.Request;
import okhttp3.Callback;

public interface HttpClient {

    /**
     * 执行同步请求, 会阻塞
     *
     * @param request  请求对象
     * @param callback 回调方法
     */
    void execute(Request request, Callback callback);

    /**
     * 执行异步请求
     *
     * @param request  请求对象
     * @param callback 回调方法
     */
    void executeAsync(Request request, Callback callback);

    /**
     * 关闭请求客户端
     */
    void close();
}
