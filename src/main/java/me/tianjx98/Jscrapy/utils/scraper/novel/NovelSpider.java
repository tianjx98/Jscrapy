package me.tianjx98.Jscrapy.utils.scraper.novel;

import me.tianjx98.Jscrapy.core.Spider;
import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

/**
 * @ClassName NovelSpider
 * @Description TODO
 * @Author tianjx98
 * @Date 2019-08-18 14:50
 */
public class NovelSpider extends Spider {
    {
        name = "novelSpider";
        startUrls.add("http://dongyeguiwu.zuopinj.com/5525/");
    }

    public static void main(String[] args) {
        start(NovelSpider.class);
    }

    @Override
    protected Function<Response, List<Request>> startUrlsCallback() {
        return this::parseMenu;
    }

    private List<Request> parseMenu(Response response) {
        Elements chapterElements = response.css("body > div.content > div:nth-child(2) > div > ul > li > a");
        String novelName = response.css("body > div.content > div:nth-child(1) > div > div.infos > h1").text();
        int chapterNum = 1;
        ArrayList<Request> chapterRequests = new ArrayList<>(chapterElements.size());
        for (Element chapterElement : chapterElements) {
            Request request = Request.builder(chapterElement.attr("href"), this)
                    .callback(this::parseChapter)
                    .addData("chapterNum", chapterNum++)
                    .addData("chapterName", chapterElement.text())
                    .addData("novelName", novelName)
                    .build();
            chapterRequests.add(request);
        }
        return chapterRequests;
    }

    private List<Request> parseChapter(Response response) {
        Request request = response.getRequest();
        String text = response.css("#htmlContent > p").text();
        NovelItem novelItem = NovelItem.builder()
                .chapterNum((Integer) request.getData("chapterNum"))
                .chapterName((String) request.getData("chapterName"))
                .novelName((String) request.getData("novelName"))
                .content(text)
                .build();
        process(novelItem);
        return null;
    }
}
