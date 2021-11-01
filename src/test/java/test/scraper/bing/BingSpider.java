package test.scraper.bing;

import me.tianjx98.Jscrapy.core.impl.Spider;
import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.Response;

import java.util.List;

/**
 * @ClassName BingSpider
 * @Description TODO
 * @Author tianjx98
 * @Date 2019-08-15 21:05
 */
public class BingSpider extends Spider {
    {
        name = "test/scraper/bing";
        startUrls.add("https://bing.ioliu.cn/?p=1");
    }

    @Override
    public List<Request> parse(Response response) {

        return super.parse(response);
    }
}
