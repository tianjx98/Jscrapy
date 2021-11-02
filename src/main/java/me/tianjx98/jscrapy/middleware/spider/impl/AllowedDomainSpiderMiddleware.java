package me.tianjx98.jscrapy.middleware.spider.impl;

import me.tianjx98.jscrapy.core.impl.Spider;
import me.tianjx98.jscrapy.http.impl.Request;
import me.tianjx98.jscrapy.http.impl.Response;
import me.tianjx98.jscrapy.middleware.spider.SpiderMiddleware;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;

/**
 * @ClassName AllowedDomainSpiderMiddleware
 * @Author tianjx98
 * @Date 2019-08-02 16:33
 */
public class AllowedDomainSpiderMiddleware extends SpiderMiddleware {
    private static final Logger LOGGER = LoggerFactory.getLogger(AllowedDomainSpiderMiddleware.class);

    /**
     * 过滤掉不在允许访问的域名范围内的请求
     *
     * @param response 当前处理的响应
     * @param requests 从响应中提取出的新的请求
     * @param spider   处理当前响应的Spider
     */
    @Override
    protected void processSpiderOutput(Response response, List<Request> requests, Spider spider) {
        if (requests == null) {
            return;
        }
        HashSet<String> allowedDomains = spider.getAllowedDomains();
        if (spider.getAllowedDomains().size() == 0) return;
        requests.removeIf((request -> {
            boolean isRemove = !allowedDomains.contains(request.getDomain());
            if (isRemove) LOGGER.info("丢弃请求" + request + ", 请求的域名[" + request.getDomain() + "]不在指定范围内");
            return isRemove;
        }));
    }
}
