package test.example;

import cn.tianjx98.jscrapy.pipeline.Item;
import lombok.Data;

/**
 * 定义一个实体类来封装解析出的数据
 *
 * @author tianjx98
 * @date 2021/11/4 15:20
 */
@Data
public class SimpleItem extends Item {
    String body;

    public SimpleItem(String body) {
        this.body = body;
    }
}
