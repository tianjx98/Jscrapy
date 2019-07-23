package me.tianjx98.Jscrapy.core;

import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.Response;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;

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
    protected void start() {
        // 生成初始请求
        for (Spider spider : spiders) {
            for (Request request : spider.startRequests()) {
                scheduler.addRequest(request);
            }
        }
        LOGGER.info("Spiders : " + spiders);
        // 初始化pipeline
        pipelineManager.openPipelines();
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
                client.execute(request.getRequest(), new SpiderCallback(this, request));
            }
        }

    }

    /**
     * 爬虫的回调类，收到响应后会调用该类里面的回调函数
     */
    static class SpiderCallback implements FutureCallback<HttpResponse> {
        private final Engine engine;
        private final Request request;

        /**
         * @param engine  爬虫引擎
         * @param request 请求类
         */
        SpiderCallback(Engine engine, Request request) {
            this.engine = engine;
            this.request = request;
        }

        /**
         * 当请求成功后执行此方法
         *
         * @param result 响应对象
         */
        @Override
        public void completed(HttpResponse result) {
            //
            engine.crawling.remove(request);
            // 调用请求类里面的回调函数，返回值可能为一个Request对象，也可能是一个Request对象数组
            Object req = request.callback(new Response(request, result));
            if (req instanceof Request) {
                engine.scheduler.addRequest((Request) req);
            }
            if (req instanceof Request[]) {
                Request[] reqs = (Request[]) req;
                for (Request r : reqs) {
                    engine.scheduler.addRequest(r);
                }
            }
            engine.nextRequest();
        }

        @Override
        public void failed(Exception ex) {

        }

        @Override
        public void cancelled() {

        }
    }
}
