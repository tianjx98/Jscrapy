import me.tianjx98.Jscrapy.core.Spider;
import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.Response;

import java.util.List;

import static java.util.Arrays.asList;

/**
 * @ClassName TestSpider
 * @Description TODO
 * @Author tianjx98
 * @Date 2019-07-29 07:59
 */
public class TestSpider extends Spider {
    String url = "https://www.baidu.com";

    {
        name = "Test";
        startUrls.add("https://www.bilibili.com");
        allowedDomains.add("www.bilibili.com");
    }

    @Override
    public List<Request> parse(Response response) {
        System.out.println(response.getResponse().getStatusLine());
        //return response.follow(url, builder -> {
        //    builder.callback(this::parse)
        //            .doFilter(false);
        //});
        return Request.builder(url, this)
                .callback(this::parse)
                .build()
                .asList();
    }

    public static void main(String[] args) {
        start(TestSpider.class);
    }
}
