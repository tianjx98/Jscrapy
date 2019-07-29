package me.tianjx98.Jscrapy.core;

import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.Response;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;

/**
 * 核心引擎，负责控制和调度各个组件，保证数据流转
 *
 * @ClassName Engine
 * @Author tian
 * @Date 2019/7/20 9:33
 * @Version 1.0
 */
public class Engine extends BasicEngine {
    private static final Logger LOGGER = LoggerFactory.getLogger(Engine.class);

    //最大并发数
    private int MAX_CONCURRENT_NUM = 5;
    private HashSet<Request> crawling = new HashSet<>(MAX_CONCURRENT_NUM);

    Engine() {
    }

    Engine(Class<? extends Spider> clazz) {
        super(clazz);
    }

    /**
     * 调用爬虫类里面的startRequests方法来生成初始请求，启动爬虫
     */
    void start() {
        // 生成初始请求
        for (Spider spider : spiders) {
            for (Request request : spider.startRequests()) {
                scheduler.addRequest(request);
            }
        }
        LOGGER.info("Spiders : " + spiders);

        try {
            // 初始化pipeline
            pipelineManager.openPipelines();
            // 初始化爬虫中间件
            spiderMiddlewareManager.openSpiderMiddlewares();
        } catch (Exception e) {
            e.printStackTrace();
        }
        nextRequest();
    }

    private void nextRequest() {
        //如果正在爬取的列表为空，调度器中也为空，则停止爬取
        if (scheduler.isEmpty() && crawling.size() == 0) {
            client.close();
            pipelineManager.closePipelines();
        }
        //如果正在爬取的数量小于最大并发数，就向正在爬取的集合里面添加请求
        while (!scheduler.isEmpty() && crawling.size() < MAX_CONCURRENT_NUM) {
            if (!scheduler.isEmpty()) {
                //从调度器取请求
                Request request = scheduler.nextRequest();
                //将请求加到正在爬取的集合
                crawling.add(request);
                // 发送请求
                client.execute(request.getRequest(), new SpiderCallback(request));
            }
        }


    }

    /**
     * 爬虫的回调类，收到响应后会调用该类里面的回调函数
     */
    class SpiderCallback implements FutureCallback<HttpResponse> {
        private final Request request;

        /**
         * @param request 请求类
         */
        SpiderCallback(Request request) {
            this.request = request;
        }

        /**
         * 当请求成功后执行此方法
         *
         * @param result 响应对象
         */
        @Override
        public void completed(HttpResponse result) {
            // 请求完成后，移除正在爬取的请求
            crawling.remove(request);
            // 调用请求类里面的回调函数，返回值可能为一个Request对象，也可能是一个Request对象数组
            Response response = new Response(request, result);
            List<Request> requests = null;
            try {
                // 爬虫中间件处理传入Spider的响应
                spiderMiddlewareManager.processInput(response);
                // 调用Spider的回调函数，返回Spider产生的新的请求
                requests = request.callback(response);
                // 爬虫中间件处理Spider产生的新的请求
                spiderMiddlewareManager.processOutput(response, requests);
            } catch (Exception e) {
                spiderMiddlewareManager.processException(response, e);
            }
            // 将处理后的请求加到调度器
            scheduler.addRequest(requests, null);
            nextRequest();
        }

        @Override
        public void failed(Exception ex) {
            // 请求完成后，移除正在爬取的请求
            crawling.remove(request);
            LOGGER.error(ex.getMessage());
        }

        @Override
        public void cancelled() {
            // 请求完成后，移除正在爬取的请求
            crawling.remove(request);
        }
    }
}
