package me.tianjx98.Jscrapy.middleware.spider.impl;

import me.tianjx98.Jscrapy.core.Spider;
import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.Response;
import me.tianjx98.Jscrapy.middleware.spider.SpiderMiddleware;
import me.tianjx98.Jscrapy.utils.Setting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;

/**
 * 限定爬虫深度的中间件，如果深度小于等于0，就不作限制
 *
 * @ClassName DepthSpiderMiddleware
 * @Author tianjx98
 * @Date 2019-07-29 08:10
 */
public class DepthSpiderMiddleware extends SpiderMiddleware {
    private static final Logger LOGGER = LoggerFactory.getLogger(DepthSpiderMiddleware.class);
    private int depthLimit;

    @Override
    protected void open() {
        this.depthLimit = Setting.SETTINGS.getInt("depthLimit");
    }

    /**
     * 丢弃深度超过指定限制的请求
     *
     * @param response 当前处理的响应
     * @param requests 从响应中提取出的新的请求
     * @param spider   处理当前响应的Spider
     */
    @Override
    protected void processSpiderOutput(Response response, List<Request> requests, Spider spider) {
        LOGGER.info("processSpiderOutput");
        // 获取上一个请求
        Map<Object, Object> data = response.getRequest().getDataMap();
        // 如果请求中没有深度，初始化当前请求深度为0
        if (!data.containsKey("depth")) {
            data.put("depth", 0);
        }
        // 当前请求的深度
        int depth = 1 + (int) data.get("depth");
        // 如果当前请求的深度超过限制，就丢弃这些请求
        if (depthLimit > 0 && depth > depthLimit) {
            requests.removeIf(request -> {
                LOGGER.info("丢弃请求" + request + ", 深度" + depth + "超过最大深度" + depthLimit);
                return true;
            });
            return;
        }
        // 否则设置新请求的深度
        for (Request request : requests) {
            request.addData("depth", depth);
        }
    }

    @Override
    protected void processSpiderException(Response response, Exception e, Spider spider) {
        e.printStackTrace();
    }
}
