package me.tianjx98.Jscrapy.middleware.spider;

import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @ClassName SpiderMiddlewareManager
 * @Description TODO
 * @Author tianjx98
 * @Date 2019-07-28 18:20
 */
public class SpiderMiddlewareManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(SpiderMiddlewareManager.class);
    private List<SpiderMiddleware> spiderMiddlewares = new LinkedList<>();

    public SpiderMiddlewareManager(TreeMap<Integer, SpiderMiddleware> spiderMwTreeMap) {
        for (Map.Entry<Integer, SpiderMiddleware> entry : spiderMwTreeMap.entrySet()) {
            spiderMiddlewares.add(entry.getValue());
        }
        LOGGER.info("SpiderMiddlewares = " + spiderMiddlewares);
    }

    public void openSpiderMiddlewares() {
        spiderMiddlewares.forEach(spiderMiddleware -> spiderMiddleware.open());
    }

    /**
     * 将进入Spider的响应交给爬虫中间件处理
     *
     * @param response 进入Spider的响应对象
     */
    public void processInput(Response response) {
        spiderMiddlewares.forEach(spiderMiddleware -> spiderMiddleware.processSpiderInput(response, response.getRequest().getSpider()));
    }

    /**
     * 处理Spider产生的输出（Item或Request）
     *
     * @param response Spider当前处理的相应
     * @param requests   Spider处理后的结果
     */
    public void processOutput(Response response, List<Request> requests) {
        for (SpiderMiddleware spiderMiddleware : spiderMiddlewares) {
            spiderMiddleware.processSpiderOutput(response, requests, response.getRequest().getSpider());
        }
    }

    public void processException(Response response, Exception e) {
        spiderMiddlewares.forEach(spiderMiddleware -> spiderMiddleware.processSpiderException(response, e, response.getRequest().getSpider()));
    }
}
