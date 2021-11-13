package me.tianjx98.jscrapy.pipeline;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.extern.log4j.Log4j2;
import me.tianjx98.jscrapy.core.Spider;

/**
 * @ClassName PipelineManager
 * @Description TODO
 * @Author tian
 * @Date 2019/7/22 17:30
 * @Version 1.0
 */
@Log4j2
public class PipelineManager {
    private final List<Pipeline> pipelines;
    private final Map<Class, List<Pipeline>> pipelineMap;

    /**
     * 通过pipeline初始化
     */
    public PipelineManager(List<Pipeline> pipelines) {
        this.pipelines = pipelines;
        this.pipelineMap = pipelines.stream()
                .collect(Collectors.groupingBy(Pipeline::getFirstInterfaceActualTypeArgument));
    }

    /**
     * 调用所有pipeline的open方法
     */
    public void open() {
        pipelines.forEach(Pipeline::open);
    }

    /**
     * 把Item给pipeline逐个处理
     *
     * @param item 数据对象
     */
    public Item processItem(Item item, Spider spider) {
        Object obj = item;
        for (Pipeline pipeline : pipelineMap.get(obj.getClass())) {
            if (obj != null) {
                obj = pipeline.processItem(obj, spider);
            }
        }
        return (Item) obj;
    }

    /**
     * 调用所有pipeline的close方法
     */
    public void close() {
        pipelines.forEach(Pipeline::close);
    }
}
