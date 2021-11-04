package me.tianjx98.jscrapy.pipeline;

import lombok.Data;
import me.tianjx98.jscrapy.core.Element;

/**
 * 如果需要将数据经过pipeline处理的话
 * 创建一个类继承此类，然后加上@Data注解，就可有直接使用JSON工具类进行对象和Json格式数据直接的转换
 * @ClassName Item
 * @Author tian
 * @Date 2019/7/22 17:17
 * @Version 1.0
 */
@Data
public class Item implements Element {

}
