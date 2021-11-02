package me.tianjx98.jscrapy.core;

/**
 * @author 18872653103
 * @date 2021/7/26 19:24
 */
public interface Engine {

    /**
     * 启动爬虫引擎
     */
    Engine start();

    /**
     * 暂停爬虫引擎
     */
    void pause();

    /**
     * 关闭爬虫引擎
     */
    void stop();
}
