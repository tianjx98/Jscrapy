package test;

import me.tianjx98.Jscrapy.core.Spider;
import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.pipeline.Item;
import me.tianjx98.Jscrapy.pipeline.impl.ImageDownloadPipeline;

/**
 * @ClassName PixivImageDownloadPipeline
 * @Description TODO
 * @Author tian
 * @Date 2019/7/25 19:46
 * @Version 1.0
 */
public class PixivImageDownloadPipeline extends ImageDownloadPipeline {
    @Override
    protected Object getImageRequest(Item item, Spider spider) {
        if (item instanceof PixivItem) {
            PixivItem pixivItem = (PixivItem) item;
            return Request.builder(pixivItem.getImageUrl(), spider)
                    .addHeader("referer", pixivItem.getPageUrl())
                    .addData("filename", pixivItem.getAuthor() + "-" + pixivItem.getTitle())
                    .build();
        }
        return null;
    }

    @Override
    protected String filePath(Request request) {
        String imageUrl = request.getUrl().toString();
        return request.getData().get("filename") + imageUrl.substring(imageUrl.lastIndexOf("."));
    }
}