package me.tianjx98.Jscrapy.pipeline.impl;

import com.fasterxml.jackson.databind.SequenceWriter;
import me.tianjx98.Jscrapy.core.Spider;
import me.tianjx98.Jscrapy.pipeline.Item;
import me.tianjx98.Jscrapy.pipeline.Pipeline;
import me.tianjx98.Jscrapy.utils.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName PixivPipeline
 * @Description TODO
 * @Author tian
 * @Date 2019/7/23 16:32
 * @Version 1.0
 */
public class JsonWriterPipeline extends Pipeline {
    private static final Logger LOGGER = LoggerFactory.getLogger(JsonWriterPipeline.class);

    SequenceWriter writer;

    @Override
    public void open() {
        // 设置json为易读的格式
        JSON.setPrettyFormat(true);
        try {
            // 写入的文件路径
            File file = new File(filePath());
            File parentFile = file.getParentFile();
            if (parentFile != null && !parentFile.exists()) {
                parentFile.mkdirs();
            }
            file.createNewFile();
            writer = JSON.getJsonWriter(new FileOutputStream(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Item processItem(Item item, Spider spider) {
        if (!isWrite(item)) return item;
        try {
            LOGGER.info(item.toString());
            writer.write(item);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 返回item交给下一个pipeline处理
        return item;
    }

    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 判断Item是否写入到Json文件中
     *
     * @return 如果是返回true, 否则返回false
     * @implSpec 这个方法在处理Item时会被调用, 判断该Item是否写入Json文件, 可以自定规则判断Item是否被写入, 默认所有Item都会写入
     */
    protected boolean isWrite(Item item) {
        return true;
    }

    /**
     * 生成Json文件的路径
     *
     * @return Json文件路径, 可以为相对路径,也可以为绝对路径
     * @implSpec 必须要重载这个方法, 否则会抛出UnsupportedOperationException异常, 通过这个方法生成文件路径, 然后在open方法中会根据这个路径生成文件写入数据
     */
    protected String filePath() {
        throw new UnsupportedOperationException();
    }
}
