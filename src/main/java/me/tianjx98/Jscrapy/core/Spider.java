package me.tianjx98.Jscrapy.core;

import com.typesafe.config.Config;
import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.Response;
import me.tianjx98.Jscrapy.utils.Setting;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.function.Function;

/**
 * 用户自己编写的爬虫脚本，可自定义抓取意图
 * @ClassName Spider
 * @Description TODO
 * @Author tian
 * @Date 2019/7/20 9:14
 * @Version 1.0
 */
public abstract class Spider {
    protected Config settings= Setting.settings;
    protected Engine engine;
    protected HashSet<String> allowedDomains = new HashSet<>();
    protected HashSet<String> startUrls = new HashSet<>();

    /**
     * 生成起始请求
     * @return  返回一个List，里面包含了所有起始请求，请求url来自startUrls
     */
    protected LinkedList<Request> startRequests() {
        LinkedList<Request> requests = new LinkedList<>();
        for (String url : startUrls) {
            requests.add(Request.builder(url).callback(startUrlsCallback()).build());
        }
        return requests;
    }

    /**
     * 起始请求的默认回调函数
     * 如果需要改变，重写此方法
     * @return  指定的回调函数
     */
    protected Function<Response, Object> startUrlsCallback() {
        return this::parse;
    }

    /**
     * 起始请求默认以此方法为回调方法
     * 用于析请求，返回值如果为Request或List<Request>类型，会自动将请求添加到队列中继续爬取
     *
     * @param response  响应内容
     * @return
     */
    public Object parse(Response response) {

        return null;
    }

    protected static void start(Class<? extends Spider> clazz){
        new Engine(clazz).start();
    }

    public void setEngine(Engine engine) {
        this.engine = engine;
    }
}
