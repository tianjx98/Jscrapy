package test.engine;

import lombok.extern.log4j.Log4j2;
import me.tianjx98.jscrapy.core.Spider;
import me.tianjx98.jscrapy.core.annotation.Order;
import me.tianjx98.jscrapy.core.annotation.ScraperElement;
import me.tianjx98.jscrapy.pipeline.Pipeline;

/**
 * @author tianjx98
 * @date 2021/11/4 15:06
 */
@Log4j2
@ScraperElement
@Order(1)
public class TestPipeline implements Pipeline<TestItem> {
    @Override
    public TestItem processItem(TestItem item, Spider spider) {
        log.info("item1: " + item);
        return item;
    }
}
