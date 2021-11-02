package me.tianjx98.jscrapy.middleware.spider.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.extern.log4j.Log4j2;
import me.tianjx98.jscrapy.config.JscrapyConfig;
import me.tianjx98.jscrapy.core.impl.Spider;
import me.tianjx98.jscrapy.http.impl.Request;
import me.tianjx98.jscrapy.http.impl.Response;
import me.tianjx98.jscrapy.middleware.spider.SpiderMiddleware;

/**
 * 限定爬虫深度的中间件，如果深度小于等于0，就不作限制
 *
 * @ClassName DepthSpiderMiddleware
 * @Author tianjx98
 * @Date 2019-07-29 08:10
 */
@Log4j2
public class DepthSpiderMiddleware extends SpiderMiddleware {
    @Autowired
    JscrapyConfig config;
    private int depthLimit;

    @PostConstruct
    private void init() {
        depthLimit = config.getDepthLimit();
    }

    @Override
    protected void open() {}

    /**
     * 丢弃深度超过指定限制的请求
     *
     * @param response 当前处理的响应
     * @param requests 从响应中提取出的新的请求
     * @param spider 处理当前响应的Spider
     */
    @Override
    protected void processSpiderOutput(Response response, List<Request> requests, Spider spider) {
        if (requests == null) {
            return;
        }
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
                log.info("丢弃请求" + request + ", 深度" + depth + "超过最大深度" + depthLimit);
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