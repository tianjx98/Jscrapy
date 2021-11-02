package me.tianjx98.jscrapy.pipeline.exporter;

import com.fasterxml.jackson.databind.SequenceWriter;
import me.tianjx98.jscrapy.pipeline.Item;
import me.tianjx98.jscrapy.utils.JSON;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @ClassName JsonExporter
 * @Description TODO
 * @Author tianjx98
 * @Date 2019-09-08 15:30
 */
public class JsonExporter {
    private final File file;
    private SequenceWriter writer;

    public JsonExporter(String path) {
        this(new File(path));
    }

    public JsonExporter(File file) {
        this.file = file;
        // 设置json为易读的格式
        JSON.setPrettyFormat(true);
        try {
            // 写入的文件路径
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

    public void write(Item item) {
        try {
            writer.write(item);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
