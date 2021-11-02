package me.tianjx98.jscrapy.utils.scraper.image_download;

import me.tianjx98.jscrapy.core.impl.Spider;
import me.tianjx98.jscrapy.http.impl.Request;
import me.tianjx98.jscrapy.pipeline.Item;

import java.util.List;

/**
 * @ClassName ImageDownloadPipeline
 * @Description TODO
 * @Author tianjx98
 * @Date 2020-05-28 13:46
 */
public class ImageDownloadPipeline extends me.tianjx98.jscrapy.pipeline.impl.ImageDownloadPipeline {

    @Override
    protected boolean isProcess(Item item) {
        return item instanceof ImageItem;
    }

    @Override
    protected List<Request> getImageRequest(Item item, Spider spider) {
        System.out.println(item);
        ImageItem imageItem = (ImageItem) item;
        return Request.builder(imageItem.getUrl(), spider)
                .addData("name", imageItem.name)
                .build()
                .asList();
    }

    @Override
    protected String filePath(Request request) {
        return request.getData("name") + ".jpg";
    }
}
