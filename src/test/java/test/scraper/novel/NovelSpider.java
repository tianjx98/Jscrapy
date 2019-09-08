package test.scraper.novel;

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
        name = "test/scraper/novel";
        startUrls.add("https://m.wukongzhuishu.com/dir/DouPoCangQiong/");
        //startUrls.add("https://m.wukongzhuishu.com/dir/JueShiZhanHun/");
        //startUrls.add("https://m.wukongzhuishu.com/dir/dahuojiyugongzhuqun/");
        //startUrls.add("https://m.wukongzhuishu.com/dir/nvshenlianaiji0kuaichuan0/");
        allowedDomains.add("m.wukongzhuishu.com");
    }

    public static void main(String[] args) {
        start(NovelSpider.class);
    }

    @Override
    protected Function<Response, List<Request>> startUrlsCallback() {
        return this::parseMenu;
    }

    private List<Request> parseMenu(Response response) {
        // 获取当前目录页的章节链接
        Elements chapterElements = response.select("body > section > div > div.ptm-card-content.pt-dir > ul > li > a");

        // 获取其他目录页
        Elements menus = response.select("body > div.sel.ptm-hide > div.pt-dir-sel > ul > li > a");
        String novelName = response.select("body > header > div").text();

        // 将章节加入到请求数组里面
        ArrayList<Request> requests = new ArrayList<>(chapterElements.size() + menus.size());
        for (Element element : chapterElements) {
            Request follow = response.follow(element.attr("href"), builder -> {
                builder.callback(this::parseChapter)
                        .addData("novelName", novelName)
                        .addData("chapterName", element.text().substring(2));
            });
            requests.add(follow);
        }
        // 将其他目录加入到请求数组
        List<Request> menuRequests = response.follow(menus.eachAttr("href"), this::parseMenu);
        requests.addAll(menuRequests);
        return requests;
    }

    private List<Request> parseChapter(Response response) {
        // 获取章节内容转换为字符串
        Elements p = response.select("#articlecon > p");
        String text = Jsoup.clean(p.html(), "", Whitelist.none().addTags("br"), new Document.OutputSettings().prettyPrint(true));
        text = text.replaceAll("<br>", "");
        String url = response.getRequest().getUrl().toString();
        // 章节数为url后面的html文件名
        Integer chapterNum = Integer.valueOf(url.substring(url.lastIndexOf("/") + 1, url.lastIndexOf(".")));
        Request request = response.getRequest();
        NovelItem novelItem = NovelItem.builder()
                .novelName((String) request.getData("novelName"))
                .chapterName((String) request.getData("chapterName"))
                .chapterNum(chapterNum)
                .content(text)
                .build();
        process(novelItem);
        return null;
    }
}
