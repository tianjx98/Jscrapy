package me.tianjx98.Jscrapy.pipeline;

import me.tianjx98.Jscrapy.core.impl.BasicEngine;
import me.tianjx98.Jscrapy.core.impl.Spider;

public abstract class Pipeline {

    protected BasicEngine engine;

    public void setEngine(BasicEngine engine) {
        this.engine = engine;
    }

    /**
     * 爬虫启动之前会调用此函数
     */
    protected void open() {

    }

    /**
     * 处理item，将item存储到本地或者远程服务器
     *
     * @param item 要处理的item
     * @return 如果返回值是Item对象，则该Item会继续交给下一个pipeline处理，如果返回null，则丢弃此Item
     */
    protected abstract Item processItem(Item item, Spider spider);

    /**
     * 爬虫关闭后调用此函数
     */
    protected void close() {

    }

    @Override
    public String toString() {
        return this.getClass().getName();
    }
}
