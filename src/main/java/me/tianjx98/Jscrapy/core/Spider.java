package me.tianjx98.Jscrapy.core;

import com.typesafe.config.Config;
import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.Response;
import me.tianjx98.Jscrapy.pipeline.Item;
import me.tianjx98.Jscrapy.utils.Setting;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * 用户自己编写的爬虫脚本，可自定义抓取意图
 *
 * @ClassName Spider
 * @Author tian
 * @Date 2019/7/20 9:14
 * @Version 1.0
 */
public abstract class Spider {
    protected static final Config SETTINGS = Setting.SETTINGS;
    /**
     * 爬虫的名称
     */
    protected String name;
    /**
     * 允许爬取的域名，如果该set不为空，且请求的域名不在里面，则丢弃该请求
     * 如果该set为空，则访问所有请求
     */
    protected HashSet<String> allowedDomains = new HashSet<>();
    /**
     * 起始请求url，startRequests方法会根据这些url来生成请求对象，然后开始爬取
     */
    protected HashSet<String> startUrls = new HashSet<>();
    /**
     * 爬虫引擎对象
     */
    private BasicEngine engine;

    /**
     * 默认从配置文件中加载所有爬虫类，启动爬虫
     */
    public static void start() {
        new Engine().start();
    }

    /**
     * 加载指定的爬虫类来启动爬虫
     *
     * @param clazz 爬虫类对象
     */
    public static void start(Class<? extends Spider> clazz) {
        new Engine(clazz).start();
    }

    /**
     * 生成起始请求
     *
     * @return 返回一个List，里面包含了所有起始请求，请求url来自startUrls
     */
    protected List<Request> startRequests() {
        LinkedList<Request> requests = new LinkedList<>();
        for (String url : startUrls) {
            requests.add(Request.builder(url, this).callback(startUrlsCallback()).build());
        }
        return requests;
    }

    /**
     * 返回起始请求的默认回调函数，默认为this::parse
     *
     * @return 指定的回调函数
     * @implSpec 在默认的startRequests方法中会调用这个函数为初始请求添加默认的回调函数，如果需要自己为初始请求指定回调函数，可以重写此方法
     */
    protected Function<Response, List<Request>> startUrlsCallback() {
        return this::parse;
    }

    /**
     * 起始请求默认以此方法为回调方法
     * 用于析请求，返回值为List<Request>类型，爬虫会自动将这些请求添加到队列中继续爬取
     *
     * @param response 响应内容
     * @return 返回请求对象的集合
     */
    public abstract List<Request> parse(Response response);

    public void setEngine(BasicEngine engine) {
        this.engine = engine;
    }

    public HashSet<String> getAllowedDomains() {
        return allowedDomains;
    }

    /**
     * 让pipeline依次处理Item对象，可以自己编写pipeline对Item进行存储等操作
     *
     * @param item 需要处理的Item对象
     */
    protected void process(Item item) {
        engine.pipelineManager.processItem(item, this);
    }

    @Override
    public String toString() {
        return "[" + name + ":" + getClass().getName() + "]";
    }
}
