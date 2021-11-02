package me.tianjx98.jscrapy.utils.scraper.novel;

import me.tianjx98.jscrapy.pipeline.Item;
import me.tianjx98.jscrapy.pipeline.impl.JsonWriterPipeline;

/**
 * @ClassName NovelPipeline
 * @Description TODO
 * @Author tianjx98
 * @Date 2019-08-18 16:01
 */
public class NovelPipeline extends JsonWriterPipeline {
    @Override
    protected boolean isWrite(Item item) {
        return item instanceof NovelItem;
    }

    @Override
    protected String filePath() {
        return "novel/白夜行.json";
    }
}
