package me.tianjx98.Jscrapy.utils.scraper.novel;

import me.tianjx98.Jscrapy.core.Spider;
import me.tianjx98.Jscrapy.pipeline.Item;
import me.tianjx98.Jscrapy.pipeline.Pipeline;
import me.tianjx98.Jscrapy.pipeline.impl.JsonWriterPipeline;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

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
