package me.tianjx98.jscrapy.pipeline.impl;

import me.tianjx98.jscrapy.core.Spider;
import me.tianjx98.jscrapy.http.impl.Request;
import me.tianjx98.jscrapy.http.impl.Response;
import me.tianjx98.jscrapy.pipeline.Item;
import me.tianjx98.jscrapy.pipeline.Pipeline;
import me.tianjx98.jscrapy.utils.scraper.image_download.ImageItem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

/**
 * 下载图片的抽象类
 *
 * @ClassName ImageDownloadPipeline
 * @Description TODO
 * @Author tian
 * @Date 2019/7/25 15:45
 * @Version 1.0s
 */
public abstract class ImageDownloadPipeline implements Pipeline<ImageItem> {
    private String imageStore = "images/";

    @Override
    public void open() {

    }

    @Override
    public ImageItem processItem(ImageItem item, Spider spider) {
        //if (isProcess(item)) {
        //    List<Request> imageRequest = getImageRequest(item, spider);
        //    this.engine.getScheduler().addRequest(imageRequest, request -> {
        //        request.setCallback(this::saveImage);
        //        request.setSpider(spider);
        //    });
        //}
        return item;
    }

    /**
     * 判断哪些Item交给这个Pipeline当做图片处理
     *
     * @param item 即将处理的item对象
     * @return 如果返回true, 则会处理这个item
     */
    protected abstract boolean isProcess(Item item);

    /**
     * 将图片信息写到文件
     *
     * @param response 图片请求的响应
     * @return
     */
    private List<Request> saveImage(Response response) {
        String filePath = filePath(response.getRequest());
        // 如果没有重写获取路径的方法，使用配置文件的默认路径
        if (filePath == null) filePath = imageStore;
        Path path = Paths.get(filePath);
        // 如果不是绝对路径就在配置文件存储图片的路径基础上拼接
        if (!path.isAbsolute()) {
            path = Paths.get(imageStore, path.toString());
        }
        // 如果路径不存在就创建路径
        path.getParent().toFile().mkdirs();
        // 如果文件不存在就创建文件，已经存在就不会创建
        try {
            File file = path.toFile();
            file.createNewFile();
            try (FileOutputStream out = new FileOutputStream(file)) {
                response.getResponse().getEntity().writeTo(out);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void close() {

    }

    /**
     * 根据Item中内容生成图片地址的请求对象
     *
     * @param item   包含图片url的Item
     * @param spider 产生item的Spider
     * @return 图片请求对象集合
     * @implSpec 生成图片的请求对象，父类会根据这些请求对象自动发送请求
     */
    protected abstract List<Request> getImageRequest(Item item, Spider spider);

    /**
     * 生成图片的存储路径，默认存储在配置文件imageStore路径的images下，生成一个以UUID.jpg命名的文件
     *
     * @param request 图片的请求对象，可以从里面读取图片信息
     * @return 存储图片的路径
     * @implSpec 生成一个绝对路径或相对路径的字符串，如果是相对路径，就以配置文件中的imageStore为根路径拼接
     */
    protected String filePath(Request request) {
        return "images/" + UUID.randomUUID() + ".jpg";
    }
}
