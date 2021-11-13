package cn.tianjx98.jscrapy.core;

/**
 * @author tianjx98
 * @date 2021/7/26 19:24
 */
public interface Engine {

    /**
     * 启动爬虫引擎
     */
    Engine start();

    void nextRequest();

    /**
     * 暂停爬虫引擎
     */
    void pause();

    void proceed();

    /**
     * 关闭爬虫引擎
     */
    void stop();
}
