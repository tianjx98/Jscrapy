package me.tianjx98.Jscrapy.middleware.download;

import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.Response;

/**
 * @ClassName DownloadMiddleware
 * @Description TODO
 * @Author tianjx98
 * @Date 2019-08-17 15:39
 */
public abstract class DownloadMiddleware {

    /**
     * 爬虫启动之前会调用此函数，可以对此中间件进行初始化
     */
    void open() {

    }

    /**
     * 处理进入下载器的请求
     *
     * @param request 被处理的请求对象
     * @return
     */
    Request processRequest(Request request) {
        return request;
    }

    Response processResponse(Response response) {

        return response;
    }

    void processException(Request request, Exception e) {

    }

    void close() {

    }
}
