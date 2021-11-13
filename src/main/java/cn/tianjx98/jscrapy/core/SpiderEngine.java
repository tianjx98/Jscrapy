package cn.tianjx98.jscrapy.core;

import cn.tianjx98.jscrapy.pipeline.Pipeline;
import com.google.common.collect.Lists;

import lombok.extern.log4j.Log4j2;
import cn.tianjx98.jscrapy.config.JscrapyConfig;
import cn.tianjx98.jscrapy.core.impl.DefaultSpiderEngine;

/**
 * 爬虫引擎启动类
 *
 * @author tianjx98
 * @date 2021/11/1 12:51
 */
@Log4j2
public class SpiderEngine {

    /**
     * 指定一个爬虫类来启动爬虫引擎
     *
     * @param spiderClass 爬虫类
     * @return {@link Engine}
     * @throws InstantiationException 实例化异常
     * @throws IllegalAccessException 非法访问异常
     */
    public static Engine start(Class<? extends Spider> spiderClass)
            throws InstantiationException, IllegalAccessException {
        return new DefaultSpiderEngine(defaultConfig(), Lists.newArrayList(spiderClass.newInstance())).start();
    }

    /**
     * 扫描指定包路径下的类实例化, 然后启动爬虫引擎<br>
     * 1.{@link Spider}的实现类<br>
     * 2.{@link Pipeline}的实现类
     *
     * @param classPackage 包名
     * @return {@link Engine}
     * @throws InstantiationException 实例化异常
     * @throws IllegalAccessException 非法访问异常
     */
    public static Engine start(String classPackage) throws InstantiationException, IllegalAccessException {
        return new DefaultSpiderEngine(defaultConfig(), classPackage).start();
    }

    private static JscrapyConfig defaultConfig() {
        return new JscrapyConfig();
    }

}
