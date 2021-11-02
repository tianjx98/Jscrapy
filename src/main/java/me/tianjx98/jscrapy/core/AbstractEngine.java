package me.tianjx98.jscrapy.core;

import java.util.LinkedList;
import java.util.List;

import lombok.extern.log4j.Log4j2;
import me.tianjx98.jscrapy.config.JscrapyConfig;
import me.tianjx98.jscrapy.core.impl2.DefaultDownloader;
import me.tianjx98.jscrapy.core.impl2.DefaultScheduler;
import me.tianjx98.jscrapy.middleware.spider.SpiderMiddlewareManager;
import me.tianjx98.jscrapy.pipeline.PipelineManager;

/**
 * @author tianjx98
 * @date 2021/11/1 12:54
 */
@Log4j2
public abstract class AbstractEngine implements Engine {
    protected JscrapyConfig config;
    protected final List<Spider> spiders = new LinkedList<>();
    protected final Downloader downloader;
    /**
     * 调度器
     */
    protected final Scheduler scheduler;
    /**
     * 处理Item的管道，用于存储数据
     */
    protected final PipelineManager pipelineManager;

    protected final SpiderMiddlewareManager spiderMiddlewareManager;

    protected AbstractEngine(JscrapyConfig config) {
        this.config = config;
        this.downloader = createDownloader();
        this.scheduler = createScheduler();
        this.pipelineManager = createPipelineManager();
        this.spiderMiddlewareManager = createSpiderMiddlewareManager();
        initEngine();
    }

    protected Downloader createDownloader() {
        return new DefaultDownloader();
    }

    protected Scheduler createScheduler() {
        return new DefaultScheduler();
    }

    protected PipelineManager createPipelineManager() {

        return null;
    }

    protected SpiderMiddlewareManager createSpiderMiddlewareManager() {
        return null;
    }

    protected void initEngine() {

    }

}
