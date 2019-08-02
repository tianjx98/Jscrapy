package me.tianjx98.Jscrapy.middleware.spider;

import lombok.extern.java.Log;
import me.tianjx98.Jscrapy.core.BasicEngine;
import me.tianjx98.Jscrapy.core.Spider;
import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.Response;
import me.tianjx98.Jscrapy.middleware.spider.impl.DepthSpiderMiddleware;
import me.tianjx98.Jscrapy.pipeline.Item;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * 处理Spider发出的请求，进入Spider的响应信息，还有Spider产生的Item
 *
 * @ClassName SpiderMiddleware
 * @Description TODO
 * @Author tianjx98
 * @Date 2019-07-28 16:05
 */
public abstract class SpiderMiddleware {
    protected static final Logger LOGGER = LoggerFactory.getLogger(SpiderMiddleware.class);
    protected BasicEngine engine;

    public void setEngine(BasicEngine engine) {
        this.engine = engine;
    }

    /**
     * 爬虫启动之前会调用此函数，可以对此中间件进行初始化
     */
    protected void open() {
    }

    /**
     * 处理进入Spider的响应，响应在进入Spider之前都会交给此方法处理，如果有多个爬虫中间件，会按照优先级顺序依次处理
     *
     * @param response 进入Spider的响应对象
     * @param spider   该Spider对象
     */
    protected void processSpiderInput(Response response, Spider spider) {
    }

    /**
     * 处理Spider产生的请求，Spider产生的请求都会按照优先级顺序依次交给每个爬虫中间件处理
     *
     * @param response 当前处理的响应
     * @param requests 从响应中提取出的新的请求
     * @param spider   处理当前响应的Spider
     */
    protected void processSpiderOutput(Response response, List<Request> requests, Spider spider) {

    }

    //protected void processSpiderItem(Response response, Item item, Spider spider) {
    //
    //}

    /**
     * 爬虫中以及爬虫中间件产生的异常可以经过这里处理
     *
     * @param response 产生异常的请求响应
     * @param e        产生的异常
     * @param spider   产生异常的爬虫
     */
    protected void processSpiderException(Response response, Exception e, Spider spider) {

    }


    @Override
    public String toString() {
        return this.getClass().getName();
    }
}