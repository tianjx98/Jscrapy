package test.scraper.pixiv;

import me.tianjx98.jscrapy.core.Spider;
import me.tianjx98.jscrapy.http.impl.Request;
import me.tianjx98.jscrapy.pipeline.Item;
import me.tianjx98.jscrapy.pipeline.impl.ImageDownloadPipeline;

import java.util.List;

/**
 * @ClassName PixivImageDownloadPipeline
 * @Description TODO
 * @Author tian
 * @Date 2019/7/25 19:46
 * @Version 1.0
 */
public class PixivImageDownloadPipeline extends ImageDownloadPipeline {
    @Override
    protected boolean isProcess(Item item) {
        return item instanceof PixivItem;
    }

    @Override
    protected List<Request> getImageRequest(Item item, Spider spider) {
        if (item instanceof PixivItem) {
            // 从item中获取图片url然后生成请求
            PixivItem pixivItem = (PixivItem) item;
            Request request = pixivItem.getRequest();
            request.addData("filename", pixivItem.getAuthor() + "-" + pixivItem.getTitle());
            return request.asList();
        }
        return null;
    }

    @Override
    protected String filePath(Request request) {
        String imageUrl = request.getUrl().toString();
        return request.getDataMap().get("filename") + imageUrl.substring(imageUrl.lastIndexOf("."));
    }
}
