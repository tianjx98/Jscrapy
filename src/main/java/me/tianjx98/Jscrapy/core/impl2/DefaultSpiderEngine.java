package me.tianjx98.Jscrapy.core.impl2;

import me.tianjx98.Jscrapy.config.JscrapyConfig;
import me.tianjx98.Jscrapy.core.AbstractEngine;
import me.tianjx98.Jscrapy.core.Engine;

/**
 * @author tianjx98
 * @date 2021/11/1 13:05
 */
public class DefaultSpiderEngine extends AbstractEngine {
    public DefaultSpiderEngine(JscrapyConfig config) {
        super(config);
    }

    @Override
    public Engine start() {
        return this;
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }
}
