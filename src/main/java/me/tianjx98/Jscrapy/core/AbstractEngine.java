package me.tianjx98.Jscrapy.core;

import java.util.LinkedList;
import java.util.List;

import lombok.extern.log4j.Log4j2;
import me.tianjx98.Jscrapy.config.JscrapyConfig;
import me.tianjx98.Jscrapy.core.impl.Downloader;
import me.tianjx98.Jscrapy.core.impl.Scheduler;
import me.tianjx98.Jscrapy.core.impl.Spider;
import me.tianjx98.Jscrapy.middleware.spider.SpiderMiddlewareManager;
import me.tianjx98.Jscrapy.pipeline.PipelineManager;

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
        return null;
    }

    protected abstract Scheduler createScheduler();

    protected abstract PipelineManager createPipelineManager();

    protected abstract SpiderMiddlewareManager createSpiderMiddlewareManager();

    protected abstract void initEngine();

}
