package me.tianjx98.jscrapy.core;

import com.google.common.collect.Lists;

import lombok.extern.log4j.Log4j2;
import me.tianjx98.jscrapy.config.JscrapyConfig;
import me.tianjx98.jscrapy.core.impl2.DefaultSpiderEngine;

/**
 * @author tianjx98
 * @date 2021/11/1 12:51
 */
@Log4j2
public class SpiderEngine {

    public static Engine start(Class<? extends Spider> spiderClass)
                    throws InstantiationException, IllegalAccessException {
        return new DefaultSpiderEngine(defaultConfig(), Lists.newArrayList(spiderClass.newInstance())).start();
    }

    public static Engine start(String classPackage) throws InstantiationException, IllegalAccessException {
        return new DefaultSpiderEngine(defaultConfig(), classPackage).start();
    }

    private static JscrapyConfig defaultConfig() {
        return new JscrapyConfig();
    }

}
