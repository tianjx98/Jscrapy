package test.example;

import cn.tianjx98.jscrapy.core.Spider;
import cn.tianjx98.jscrapy.core.annotation.Order;
import cn.tianjx98.jscrapy.core.annotation.ScraperElement;
import cn.tianjx98.jscrapy.pipeline.Pipeline;
import lombok.extern.log4j.Log4j2;
import test.engine.TestItem;

/**
 * 可以使用Pipeline完成数据持久化的逻辑<br>
 * 采用了过滤器设计模式, 可以定义多个Pipeline对数据进行处理<br>
 * 注意: 如果模板参数为SimpleItem, 那么这个Pipeline就只会处理类型为SimpleItem的Item
 *
 * @author tianjx98
 * @date 2021/11/4 15:06
 */
@Log4j2
@ScraperElement
@Order(1)
public class SimplePipeline implements Pipeline<SimpleItem> {

    @Override
    public void open() {
        // 启动时执行此方法
    }

    @Override
    public SimpleItem processItem(SimpleItem item, Spider spider) {
        log.info("SimplePipeline: " + item);
        // 保存数据
        return item;
    }

    @Override
    public void close() {
        // 结束时执行此方法
    }
}
