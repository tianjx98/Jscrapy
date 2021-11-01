package me.tianjx98.Jscrapy.core;

import me.tianjx98.Jscrapy.config.JscrapyConfig;
import me.tianjx98.Jscrapy.core.impl2.DefaultSpiderEngine;

/**
 * @author tianjx98
 * @date 2021/11/1 12:51
 */
public class SpiderEngine {

    /**
     * 启动爬虫引擎, 使用指定配置文件
     * 
     * @param config 配置文件类
     * @return 爬虫引擎
     */
    public static Engine start(JscrapyConfig config) {
        return new DefaultSpiderEngine(config).start();
    }

    /**
     * 启动爬虫引擎, 使用默认配置文件
     *
     * @return 爬虫引擎
     */
    public static Engine start() {
        final JscrapyConfig jscrapyConfig = new JscrapyConfig();
        return start(jscrapyConfig);
    }
}
