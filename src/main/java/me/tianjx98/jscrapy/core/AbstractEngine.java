package me.tianjx98.jscrapy.core;

import java.util.List;

import lombok.extern.log4j.Log4j2;
import me.tianjx98.jscrapy.config.JscrapyConfig;
import me.tianjx98.jscrapy.core.annotation.ScraperElement;
import me.tianjx98.jscrapy.core.impl2.DefaultDownloader;
import me.tianjx98.jscrapy.core.impl2.DefaultScheduler;
//import me.tianjx98.jscrapy.middleware.spider.SpiderMiddlewareManager;
import me.tianjx98.jscrapy.pipeline.Pipeline;
import me.tianjx98.jscrapy.pipeline.PipelineManager;
import me.tianjx98.jscrapy.utils.ClassUtil;

/**
 * @author tianjx98
 * @date 2021/11/1 12:54
 */
@Log4j2
public abstract class AbstractEngine implements Engine {
    protected JscrapyConfig config;
    protected volatile EngineState state;
    protected List<Spider> spiders;
    protected final Downloader downloader;
    /**
     * 调度器
     */
    protected final Scheduler scheduler;
    /**
     * 处理Item的管道，用于存储数据
     */
    protected PipelineManager pipelineManager;

    //protected SpiderMiddlewareManager spiderMiddlewareManager;


    protected AbstractEngine(JscrapyConfig config, List<Spider> spiders) {
        this.config = config;
        this.spiders = spiders;
        this.downloader = createDownloader();
        this.scheduler = createScheduler();
        initEngine();
    }

    protected AbstractEngine(JscrapyConfig config, String classPackage)
                    throws InstantiationException, IllegalAccessException {
        this.config = config;
        final ClassUtil classUtil = ClassUtil.of(classPackage);
        this.spiders = loadAllSpiders(classUtil);
        this.downloader = createDownloader();
        this.scheduler = createScheduler();
        this.pipelineManager = createPipelineManager(classUtil);
        //this.spiderMiddlewareManager = createSpiderMiddlewareManager();
        initEngine();
    }

    protected List<Spider> loadAllSpiders(ClassUtil classUtil) throws InstantiationException, IllegalAccessException {
        return classUtil.getInstanceByClassAndAnnotation(Spider.class, ScraperElement.class);
    }

    protected Downloader createDownloader() {
        return new DefaultDownloader();
    }

    protected Scheduler createScheduler() {
        return new DefaultScheduler();
    }

    protected PipelineManager createPipelineManager(ClassUtil classUtil)
                    throws InstantiationException, IllegalAccessException {
        return new PipelineManager(classUtil.getInstanceByClassAndAnnotation(Pipeline.class, ScraperElement.class));
    }

    //protected SpiderMiddlewareManager createSpiderMiddlewareManager() {
    //    return null;
    //}

    protected void initEngine() {

    }

    @Override
    public void pause() {
        state = EngineState.PAUSE;
    }

    @Override
    public void proceed() {
        state = EngineState.RUN;
        nextRequest();
    }

    @Override
    public void stop() {

    }

    protected abstract void nextRequest();

}
