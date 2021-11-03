package me.tianjx98.jscrapy.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;

import lombok.extern.log4j.Log4j2;
import me.tianjx98.jscrapy.config.JscrapyConfig;
import me.tianjx98.jscrapy.core.impl2.DefaultSpiderEngine;
import me.tianjx98.jscrapy.utils.ClassUtil;

/**
 * @author tianjx98
 * @date 2021/11/1 12:51
 */
@Log4j2
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

    public static Engine start(Class<? extends Spider> spiderClass)
                    throws InstantiationException, IllegalAccessException {
        final JscrapyConfig jscrapyConfig = new JscrapyConfig();
        return new DefaultSpiderEngine(jscrapyConfig, Lists.newArrayList(spiderClass.newInstance())).start();
    }

    public static Engine start(Set<Class<? extends Spider>> spiderClasses)
                    throws InstantiationException, IllegalAccessException {
        final JscrapyConfig jscrapyConfig = new JscrapyConfig();
        final List<Spider> spiders = new ArrayList<>(spiderClasses.size());
        for (Class<? extends Spider> spiderClass : spiderClasses) {
            spiders.add(spiderClass.newInstance());
        }
        final DefaultSpiderEngine engine = new DefaultSpiderEngine(jscrapyConfig, spiders);
        return engine.start();
    }

    public static Engine start(String classPackage) throws InstantiationException, IllegalAccessException {
        return new DefaultSpiderEngine(defaultConfig(), ClassUtil.getInstanceByAnnotation(classPackage,
                        me.tianjx98.jscrapy.core.annotation.Spider.class, Spider.class)).start();
    }

    private static JscrapyConfig defaultConfig() {
        return new JscrapyConfig();
    }

    private static Spider newInstance(Class<?> cls) throws InstantiationException, IllegalAccessException {
        return (Spider) cls.newInstance();
    }
}
