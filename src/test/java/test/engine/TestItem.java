package test.engine;

import lombok.Data;
import me.tianjx98.jscrapy.pipeline.Item;

/**
 * @author tianjx98
 * @date 2021/11/4 15:20
 */
@Data
public class TestItem extends Item {
    String body;

    public TestItem(String body) {
        this.body = body;
    }
}
