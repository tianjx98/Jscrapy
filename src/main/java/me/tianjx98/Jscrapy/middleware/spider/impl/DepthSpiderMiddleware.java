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

    @Override
    protected void processSpiderOutput(Response response, List<Request> requests, Spider spider) {
        LOGGER.info("processSpiderOutput");
        Map<Object, Object> data = response.getRequest().getDataMap();
        // 如果请求中没有深度，初始化当前请求深度为0
        if (!data.containsKey("depth")) {
            data.put("depth", 0);
        }
        int depth = 1 + (int) data.get("depth");
    }

    @Override
    protected void processSpiderException(Response response, Exception e, Spider spider) {
        e.printStackTrace();
    }
}
