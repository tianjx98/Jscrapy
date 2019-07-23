package me.tianjx98.Jscrapy.core;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigValue;
import me.tianjx98.Jscrapy.duplicatefilter.DuplicateFilter;
import me.tianjx98.Jscrapy.http.client.AsyncHttpClient;
import me.tianjx98.Jscrapy.pipeline.Pipeline;
import me.tianjx98.Jscrapy.pipeline.PipelineManager;
import me.tianjx98.Jscrapy.utils.Setting;
import org.apache.http.HttpHost;
import org.apache.http.message.BasicHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * 核心引擎父类，主要在启动时通过配置文件初始化异步请求客户端，调度器，加载中间件
 *
 * @ClassName Engine
 * @Description TODO 将所有读取配置文件的过程都放到引擎类中
 * @Author tian
 * @Date 2019/7/20 9:33
 * @Version 1.0
 */
public class BasicEngine {
    protected static final Config SETTINGS = Setting.SETTINGS;
    private static final Logger LOGGER = LoggerFactory.getLogger(BasicEngine.class);
    /**
     * 启动时加载所有爬虫类放到该集合中
     */
    protected List<Spider> spiders = new LinkedList<>();
    /**
     * 异步请求客户端
     */
    protected AsyncHttpClient client = createAsyncHttpClient();
    /**
     * 调度器
     */
    protected Scheduler scheduler = createScheduler();

    protected PipelineManager pipelineManager = createPipelineManager();

    /**
     * 默认构造函数，从配置文件中读取所有的爬虫类并生成实例
     */
    BasicEngine() {
        // 从配置文件中获取所有的爬虫类名
        List<String> spidersName = SETTINGS.getStringList("spiders");
        for (String className : spidersName) {
            try {
                Class<Spider> clazz = (Class<Spider>) Class.forName(className);
                createSpiderFromClass(clazz);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 通过爬虫的class对象来创建一个爬虫，调用start()方法开始执行这个爬虫
     *
     * @param clazz
     */
    BasicEngine(Class<? extends Spider> clazz) {
        createSpiderFromClass(clazz);
    }

    /**
     * 从配置文件中读取默认的url过滤器和爬取方式来创建调度器
     *
     * @return 调度器对象
     */
    private Scheduler createScheduler() {
        Config scheduler = SETTINGS.getConfig("scheduler");
        DuplicateFilter dupFilter = null;
        try {
            dupFilter = (DuplicateFilter) Class.forName(scheduler.getString("defaultDupFilter")).getConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return new Scheduler(dupFilter, scheduler.getBoolean("bfs"));
    }

    private void createSpiderFromClass(Class<? extends Spider> clazz) {
        try {
            // 通过无参构造函数创建爬虫对象
            Spider spider = clazz.getConstructor().newInstance();
            // 爬虫名称不能为空
            if (spider.name == null || "".equals(spider.name)) {
                throw new Exception(spider.getClass().getName() + "名称不能为空");
            }
            spider.setEngine(this);
            this.spiders.add(spider);
        } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取配置文件，创建异步请求客户端
     *
     * @return
     */
    private AsyncHttpClient createAsyncHttpClient() {
        HttpHost host = null;
        try {
            //获取代理配置，如果配置文件没有则为null
            Config proxy = SETTINGS.getConfig("proxy");
            host = new HttpHost(proxy.getString("host"), proxy.getInt("port"));
        } catch (ConfigException.Missing e) {
            e.printStackTrace();
        }
        // 从配置文件中读取默认的请求头
        LinkedList<BasicHeader> headers = null;
        try {
            Config defaultHeaders = Setting.SETTINGS.getConfig("defaultHeaders");
            headers = new LinkedList<>();
            for (Map.Entry<String, ConfigValue> entry : defaultHeaders.entrySet()) {
                headers.add(new BasicHeader(entry.getKey(), entry.getValue().unwrapped().toString()));
            }
        } catch (ConfigException.Missing e) {
            e.printStackTrace();
        }
        return new AsyncHttpClient(host, headers);
    }

    /**
     * 从配置文件中读取所有的pipeline类名，然后创建对象，生成pipeline管理器
     *
     * @return pipeline管理器对象
     */
    private PipelineManager createPipelineManager() {
        Config itemPipelinesConfig = SETTINGS.getConfig("itemPipelines");
        // 因为pipeline是有优先级的，所有使用TreeMap
        TreeMap<Integer, Pipeline> pipelineTreeMap = new TreeMap<>();
        for (Map.Entry<String, ConfigValue> entry : itemPipelinesConfig.entrySet()) {
            String className = entry.getValue().unwrapped().toString();
            try {
                Pipeline pipeline = (Pipeline) Class.forName(className).getConstructor().newInstance();
                pipelineTreeMap.put(Integer.valueOf(entry.getKey()), pipeline);
            } catch (InstantiationException | NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                LOGGER.error("未找到Pipeline：" + e.getMessage() + "，请检查配置文件");
            }
        }
        return new PipelineManager(pipelineTreeMap);
    }
}
