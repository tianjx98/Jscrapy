package me.tianjx98.jscrapy.pipeline;

import me.tianjx98.jscrapy.core.GenericUtil;
import me.tianjx98.jscrapy.core.Spider;

public interface Pipeline<T> extends GenericUtil {

    /**
     * 爬虫启动之前会调用此函数
     */
    default void open() {}


    /**
     * 处理item，将item存储到本地或者远程服务器
     *
     * @param item 要处理的item
     * @return 如果返回值是Item对象，则该Item会继续交给下一个pipeline处理，如果返回null，则丢弃此Item
     */
    T processItem(T item, Spider spider);

    /**
     * 爬虫关闭后调用此函数
     */
    default void close() {}

}
