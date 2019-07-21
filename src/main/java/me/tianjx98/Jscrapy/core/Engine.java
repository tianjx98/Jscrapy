package me.tianjx98.Jscrapy.core;

import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.Response;
import me.tianjx98.Jscrapy.http.client.AsyncHttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 核心引擎，负责控制和调度各个组件，保证数据流转
 * @ClassName Engine
 * @Description TODO
 * @Author tian
 * @Date 2019/7/20 9:33
 * @Version 1.0
 */
public class Engine {
    private static Logger logger = LoggerFactory.getLogger(Engine.class);

    private Spider spider;
    private AsyncHttpClient client;

    //最大并发数
    private int MAX_CONCURRENT_NUM = 5;
    private HashSet<Request> crawling = new HashSet<>(MAX_CONCURRENT_NUM);
    private Queue<Request> scheduler = new LinkedList<>();

    Engine() {
    }

    Engine(Class<? extends Spider> spiderClass) {
        try {
            this.spider = spiderClass.getConstructor().newInstance();
            this.spider.setEngine(this);
            this.client = new AsyncHttpClient();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }

    protected void start() {
        for (Request request : spider.startRequests()) {
            scheduler.add(request);
        }
        nextRequest();
    }

    private void nextRequest() {
        //如果正在爬取的列表为空，调度器中也为空，则停止爬取
        if (scheduler.size() == 0 && crawling.size() == 0) {
            client.close();
        }
        //如果正在爬取的数量小于最大并发数，就向正在爬取的集合里面添加请求
        while (!scheduler.isEmpty() && crawling.size() < MAX_CONCURRENT_NUM) {
            if (!scheduler.isEmpty()) {
                //从调度器取请求
                Request request = scheduler.poll();
                //将请求加到正在爬取的集合
                crawling.add(request);
                // 发送请求
                client.execute(request.getRequest(), new SpiderCallback(this, request));
            }
        }

    }

    static class SpiderCallback implements FutureCallback<HttpResponse> {
        private final Engine engine;
        private final Request request;

        SpiderCallback(Engine engine, Request request) {
            this.engine = engine;
            this.request = request;
        }

        @Override
        public void completed(HttpResponse result) {
            Object req = request.callback(new Response(request, result));
            engine.crawling.remove(request);
            if (req instanceof Request) {
                engine.scheduler.add((Request) req);
            }
            if (req instanceof Request[]) {
                Request[] reqs = (Request[]) req;
                for (Request r : reqs) {
                    engine.scheduler.add(r);
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
