package me.tianjx98.jscrapy.core.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 核心引擎父类，主要在启动时通过配置文件初始化异步请求客户端，调度器，加载中间件
 *
 * @ClassName BasicEngine
 * @Author tian
 * @Date 2019/7/20 9:33
 * @Version 1.0
 */
public abstract class BasicEngine {
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicEngine.class);
    /**
     * 启动时加载所有爬虫类放到该集合中
     */
    //protected final List<Spider> spiders = new LinkedList<>();
    //
    //protected final Downloader downloader = createDownloader();
    //
    ///**
    // * 调度器
    // */
    //protected final Scheduler scheduler = createScheduler();
    //
    ///**
    // * 处理Item的管道，用于存储数据
    // */
    //protected final PipelineManager pipelineManager = createPipelineManager();
    //
    //protected final SpiderMiddlewareManager spiderMiddlewareManager = createSpiderMiddlewareManager();
    //
    //protected final Monitor monitor = new Monitor(this);

    /**
     * 默认构造函数，从配置文件中读取所有的爬虫类并生成实例
     */
    //BasicEngine() {
    //    // 从配置文件中获取所有的爬虫类名
    //    Map spidersName = SETTINGS.getSetting("spiders").getSettingsMap();
    //    for (Object className : spidersName.keySet()) {
    //        try {
    //            Class<Spider> clazz = (Class<Spider>) Class.forName((String) className);
    //            createSpiderFromClass(clazz);
    //        } catch (ClassNotFoundException e) {
    //            LOGGER.error("未找到Spider:" + e.getMessage() + ", 请检查配置文件");
    //        }
    //    }
    //}
    //
    ///**
    // * 通过爬虫的class对象来创建一个爬虫，调用start()方法开始执行这个爬虫
    // *
    // * @param clazz
    // */
    //BasicEngine(Class<? extends Spider> clazz) {
    //    createSpiderFromClass(clazz);
    //}
    //
    //public Scheduler getScheduler() {
    //    return scheduler;
    //}
    //
    //public Downloader getDownloader() {
    //    return downloader;
    //}
    //
    ///**
    // * 从配置文件中读取默认的url过滤器和爬取方式来创建调度器
    // *
    // * @return 调度器对象
    // */
    //private Scheduler createScheduler() {
    //    // 过滤器完整类名，默认为me.tianjx98.Jscrapy.duplicatefilter.impl.BloomDuplicateFilter
    //    String defaultDupFilter = SETTINGS.getString("scheduler|defaultDupFilter");
    //    LOGGER.info("DupFilter = " + defaultDupFilter);
    //
    //    // 读取失败，重新创建一个调度器
    //    DuplicateFilter dupFilter = null;
    //    try {
    //        dupFilter = (DuplicateFilter) Class.forName(defaultDupFilter).getConstructor().newInstance();
    //    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
    //        e.printStackTrace();
    //    }
    //    return new Scheduler(dupFilter, SETTINGS.getBoolean("scheduler|bfs"));
    //}
    //
    //private void createSpiderFromClass(Class<? extends Spider> clazz) {
    //    try {
    //        // 通过无参构造函数创建爬虫对象
    //        Spider spider = clazz.getConstructor().newInstance();
    //        // 爬虫名称不能为空
    //        if (spider.name == null || "".equals(spider.name)) {
    //            throw new Exception(spider.getClass().getName() + "名称不能为空");
    //        }
    //        spider.setEngine(this);
    //        this.spiders.add(spider);
    //    } catch (Exception e) {
    //        e.printStackTrace();
    //    }
    //}
    //
    ///**
    // * 读取配置文件，创建异步请求客户端
    // *
    // * @return
    // */
    //private AsyncHttpClient createAsyncHttpClient() {
    //    HttpHost host = null;
    //    LinkedList<BasicHeader> headers = null;
    //
    //    //获取代理配置，如果不使用代理则为null
    //    if (SETTINGS.getBoolean("proxy|enable")) {
    //        host = new HttpHost(SETTINGS.getString("proxy|host"), SETTINGS.getInt("proxy|port"));
    //    }
    //
    //    // 获取超时时间
    //    int connectionTimeout = SETTINGS.getInt("connectionTimeout");
    //
    //    int maxThreadNum = SETTINGS.getInt("maxThreadNum");
    //
    //    return new AsyncHttpClient(host, connectionTimeout * 1000, maxThreadNum);
    //}
    //
    //private Downloader createDownloader() {
    //    // 获取最大并发数
    //    int concurrentRequests = SETTINGS.getInt("concurrentRequests");
    //
    //    // 获取同一域名下的最大并发数
    //    int concurrentRequestsPerDomain = SETTINGS.getInt("concurrentRequestsPerDomain");
    //
    //    // 获取随机延迟(ms)
    //    int randomDownloadDelay = SETTINGS.getInt("randomDownloadDelay");
    //
    //    return new Downloader(concurrentRequests,
    //            concurrentRequestsPerDomain,
    //            randomDownloadDelay,
    //            createAsyncHttpClient());
    //}
    //
    ///**
    // * 从配置文件中读取所有的pipeline类名，然后创建对象，生成pipeline管理器
    // *
    // * @return pipeline管理器对象
    // */
    //private PipelineManager createPipelineManager() {
    //    Map itemPipelinesConfig = SETTINGS.getSetting("itemPipelines").getSettingsMap();
    //    // 因为pipeline是有优先级的，所有使用TreeMap
    //    TreeMap<Integer, Pipeline> pipelineTreeMap = new TreeMap<>();
    //    for (Object entryObj : itemPipelinesConfig.entrySet()) {
    //        Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) entryObj;
    //        String className = entry.getKey();
    //        try {
    //            Pipeline pipeline = (Pipeline) Class.forName(className).getConstructor().newInstance();
    //            pipeline.setEngine(this);
    //            pipelineTreeMap.put(entry.getValue(), pipeline);
    //        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
    //            e.printStackTrace();
    //        } catch (ClassNotFoundException e) {
    //            LOGGER.error("未找到Pipeline:" + e.getMessage() + ", 请检查配置文件");
    //        }
    //    }
    //    return new PipelineManager(pipelineTreeMap);
    //}
    //
    ///**
    // * 从配置文件中读取所有的爬虫中间件类名，然后创建对象，生成爬虫中间件管理器
    // *
    // * @return 爬虫中间件管理器
    // */
    //private SpiderMiddlewareManager createSpiderMiddlewareManager() {
    //    Map spiderMiddlewaresConfig = SETTINGS.getSetting("spiderMiddlewares").getSettingsMap();
    //    TreeMap<Integer, SpiderMiddleware> spiderMiddlewaresTreeMap = new TreeMap<>();
    //    for (Object entryObj : spiderMiddlewaresConfig.entrySet()) {
    //        Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) entryObj;
    //        String className = entry.getKey();
    //        try {
    //            SpiderMiddleware spiderMiddleware = (SpiderMiddleware) Class.forName(className).getConstructor().newInstance();
    //            spiderMiddleware.setEngine(this);
    //            spiderMiddlewaresTreeMap.put(entry.getValue(), spiderMiddleware);
    //        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
    //            e.printStackTrace();
    //        } catch (ClassNotFoundException e) {
    //            LOGGER.error("未找到SpiderMiddleware:" + e.getMessage() + ", 请检查配置文件");
    //        }
    //    }
    //    return new SpiderMiddlewareManager(spiderMiddlewaresTreeMap);
    //}

}
