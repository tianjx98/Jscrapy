package me.tianjx98.Jscrapy.pipeline;

import me.tianjx98.Jscrapy.core.impl.Spider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.Map;
import java.util.TreeMap;

/**
 * @ClassName PipelineManager
 * @Description TODO
 * @Author tian
 * @Date 2019/7/22 17:30
 * @Version 1.0
 */
public class PipelineManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(PipelineManager.class);
    private LinkedList<Pipeline> pipelines = new LinkedList<>();

    /**
     * 通过pipeline初始化
     *
     * @param pipelineTreeMap key为pipeline的优先级，value为对象
     */
    public PipelineManager(TreeMap<Integer, Pipeline> pipelineTreeMap) {
        for (Map.Entry<Integer, Pipeline> entry : pipelineTreeMap.entrySet()) {
            pipelines.addLast(entry.getValue());
        }
        LOGGER.info("Pipelines = " + pipelines);
    }

    /**
     * 调用所有pipeline的open方法
     */
    public void openPipelines() {
        pipelines.forEach((pipeline) -> pipeline.open());
    }

    /**
     * 把Item给pipeline逐个处理
     *
     * @param item
     */
    public void processItem(Item item, Spider spider) {
        for (Pipeline pipeline : pipelines) {
            Item result = pipeline.processItem(item, spider);
            if (result == null) {
                LOGGER.warn("Item" + item + "被丢弃");
                return;
            }
        }
    }

    /**
     * 调用所有pipeline的close方法
     */
    public void closePipelines() {
        pipelines.forEach((pipeline) -> pipeline.close());
    }
}
