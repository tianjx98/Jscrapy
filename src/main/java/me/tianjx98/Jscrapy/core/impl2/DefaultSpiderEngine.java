package me.tianjx98.Jscrapy.core.impl2;

import me.tianjx98.Jscrapy.config.JscrapyConfig;
import me.tianjx98.Jscrapy.core.AbstractEngine;
import me.tianjx98.Jscrapy.core.Engine;
import me.tianjx98.Jscrapy.core.impl.Scheduler;
import me.tianjx98.Jscrapy.middleware.spider.SpiderMiddlewareManager;
import me.tianjx98.Jscrapy.pipeline.PipelineManager;

/**
 * @author tianjx98
 * @date 2021/11/1 13:05
 */
public class DefaultSpiderEngine extends AbstractEngine {
    public DefaultSpiderEngine(JscrapyConfig config) {
        super(config);
    }

    @Override
    protected Scheduler createScheduler() {
        return null;
    }

    @Override
    protected PipelineManager createPipelineManager() {
        return null;
    }

    @Override
    protected SpiderMiddlewareManager createSpiderMiddlewareManager() {
        return null;
    }

    @Override
    protected void initEngine() {

    }

    @Override
    public Engine start() {
        return null;
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }
}
