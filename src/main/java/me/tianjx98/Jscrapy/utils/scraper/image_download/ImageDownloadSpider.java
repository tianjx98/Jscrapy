package me.tianjx98.Jscrapy.utils.scraper.image_download;

import me.tianjx98.Jscrapy.core.Engine;
import me.tianjx98.Jscrapy.core.Spider;
import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.Response;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.List;

/**
 * @ClassName ImageDownloadSpider
 * @Description TODO
 * @Author tianjx98
 * @Date 2020-05-28 13:31
 */
public class ImageDownloadSpider extends Spider {
    {
        name = "imageDownload";
        startUrls.add("https://www.quanjing.com/creative/");
    }

    public static void main(String[] args) {
        start(ImageDownloadSpider.class);
    }
    @Override
    public List<Request> parse(Response response) {
        Elements images = response.css("body > section > div.indexpic > ul > li > a");
        for (Element image : images) {
            ImageItem imageItem = ImageItem.builder()
                    .name(image.select("div > span").text())
                    .url(image.select("img").attr("src"))
                    .build();
            //System.out.println(imageItem);
            process(imageItem);
        }
        return null;
    }
}
