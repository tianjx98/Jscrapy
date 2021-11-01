package test.scraper.pixiv;

import com.fasterxml.jackson.databind.SequenceWriter;
import me.tianjx98.Jscrapy.core.impl.Spider;
import me.tianjx98.Jscrapy.pipeline.Item;
import me.tianjx98.Jscrapy.pipeline.Pipeline;
import me.tianjx98.Jscrapy.utils.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName PixivPipeline
 * @Description TODO
 * @Author tian
 * @Date 2019/7/23 16:32
 * @Version 1.0
 */
public class PixivPipeline extends Pipeline {
    private static final Logger LOGGER = LoggerFactory.getLogger(PixivPipeline.class);

    SequenceWriter writer;

    @Override
    public void open() {
        // 设置json为易读的格式
        JSON.setPrettyFormat(true);
        // 从配置文件中获取存储路径
        String path = "";
        try {
            File file = new File(path + "pixiv.json");
            File parentFile = file.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            file.createNewFile();
            writer = JSON.getJsonWriter(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Item processItem(Item item, Spider spider) {
        if (!(item instanceof PixivItem)) return item;
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
}
