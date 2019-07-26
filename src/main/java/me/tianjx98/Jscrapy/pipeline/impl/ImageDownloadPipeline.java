package me.tianjx98.Jscrapy.pipeline.impl;

import me.tianjx98.Jscrapy.core.Spider;
import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.Response;
import me.tianjx98.Jscrapy.pipeline.Item;
import me.tianjx98.Jscrapy.pipeline.Pipeline;
import me.tianjx98.Jscrapy.utils.Setting;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 下载图片的抽象类
 *
 * @ClassName ImageDownloadPipeline
 * @Description TODO
 * @Author tian
 * @Date 2019/7/25 15:45
 * @Version 1.0
 */
public abstract class ImageDownloadPipeline extends Pipeline {
    private String imageStore = Setting.SETTINGS.getString("imageStore");

    @Override
    public void open() {

    }

    @Override
    public Item processItem(Item item, Spider spider) {
        Object imageRequest = getImageRequest(item, spider);
        this.engine.getScheduler().addRequest(imageRequest, request -> {
            request.setCallback(this::saveImage);
            request.setSpider(spider);
        });
        return item;
    }

    /**
     * 将图片信息写到文件
     *
     * @param response 图片请求的响应
     * @return
     */
    private Object saveImage(Response response) {
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
     * 根据Item中内容生成图片地址的请求对象，然后爬虫会自动发送请求下载该图片
     *
     * @param item   包含图片url的Item
     * @param spider
     * @return 图片请求对象，可以为Request对象或List<Request>
     */
    protected abstract Object getImageRequest(Item item, Spider spider);

    /**
     * 图片的存储路径，默认存储在配置文件imageStore的路径下
     *
     * @param request
     * @return 存储图片的路径
     */
    protected String filePath(Request request) {

        return null;
    }
}
