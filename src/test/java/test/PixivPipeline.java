package test;

import com.fasterxml.jackson.databind.SequenceWriter;
import me.tianjx98.Jscrapy.core.Spider;
import me.tianjx98.Jscrapy.pipeline.Item;
import me.tianjx98.Jscrapy.pipeline.Pipeline;
import me.tianjx98.Jscrapy.utils.JSON;
import me.tianjx98.Jscrapy.utils.Setting;
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
        JSON.setPrettyFormat(true);
        String path = Setting.SETTINGS.getString("storePath");
        try {
            File file = new File(path + "pixiv.json");
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
        try {
            LOGGER.info(item.toString());
            writer.write(item);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
