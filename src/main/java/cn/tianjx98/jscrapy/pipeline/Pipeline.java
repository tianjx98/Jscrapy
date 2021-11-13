package cn.tianjx98.jscrapy.pipeline;

import cn.tianjx98.jscrapy.core.GenericUtil;
import cn.tianjx98.jscrapy.core.Spider;
import cn.tianjx98.jscrapy.core.annotation.Order;
import cn.tianjx98.jscrapy.core.annotation.ScraperElement;

/**
 * 数据处理对象, 引擎启动时会加载此接口所有的子类, 并将{@link Spider}产生的所有{@link Item}依次交给子类处理<br>
 * 注意, 子类上可以添加以下注解:<br>
 * {@link ScraperElement} 必须, 加上此注解的类才会被扫描到<br>
 * {@link Order} 可选, 优先级, value越大优先级越低
 *
 * @author tianjx98
 * @date 2021/11/13
 */
public interface Pipeline<T> extends GenericUtil {

    /**
     * 爬虫启动之前会调用此方法
     */
    default void open() {
    }

    /**
     * 处理item
     *
     * @param item 要处理的item
     * @return 如果返回值是Item对象，则该Item会继续交给下一个pipeline处理，如果返回null，则丢弃此Item, 不会进行后续处理
     */
    T processItem(T item, Spider spider);

    /**
     * 爬虫关闭后调用此方法
     */
    default void close() {
    }

}
