package me.tianjx98.jscrapy.utils.scraper.novel2;

import java.io.*;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import me.tianjx98.jscrapy.core.Spider;
import me.tianjx98.jscrapy.pipeline.Pipeline;

/**
 * @ClassName NovelPipeline
 * @Description TODO
 * @Author tianjx98
 * @Date 2019-08-18 16:01
 */
public class NovelPipeline implements Pipeline<NovelItem> {

    // 键为小说名,后面的map键为章节数
    HashMap<String, TreeMap<Integer, NovelItem>> novels;

    @Override
    public void open() {
        novels = new HashMap<>();
    }

    @Override
    public NovelItem processItem(NovelItem item, Spider spider) {
        NovelItem novelItem = (NovelItem) item;
        System.out.println("downloaded: " + novelItem.chapterName);
        TreeMap<Integer, NovelItem> novel = novels.get(novelItem.novelName);
        if (novel == null) {
            novel = new TreeMap<>();
            novels.put(novelItem.getNovelName(), novel);
        }
        novel.put(novelItem.chapterNum, novelItem);
        return item;
    }

    @Override
    public void close() {
        // 关闭时将小说存储到本地
        Charset charset = Charset.defaultCharset();
        for (Map.Entry<String, TreeMap<Integer, NovelItem>> entry : novels.entrySet()) {
            String novelName = entry.getKey();
            TreeMap<Integer, NovelItem> novel = entry.getValue();
            try (OutputStream out = new FileOutputStream(new File(novelName + ".txt"))) {
                for (NovelItem value : novel.values()) {
                    // 写入章节名
                    out.write(value.getChapterName().getBytes(charset));
                    // 章节名后面两个换行
                    out.write("\r\n\r\n".getBytes(charset));
                    // 写入章节内容
                    out.write(value.getContent().getBytes(charset));
                    out.write("\r\n\r\n".getBytes(charset));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
