import me.tianjx98.Jscrapy.core.Spider;
import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.Response;

import java.util.List;

/**
 * @ClassName TestSpider
 * @Description TODO
 * @Author tianjx98
 * @Date 2019-07-29 07:59
 */
public class TestSpider extends Spider {

    {
        name = "Test";
        startUrls.add("https://www.baidu.com");
    }

    @Override
    public List<Request> parse(Response response) {
        System.out.println(response.getResponse().getStatusLine());
        return null;
    }

    public static void main(String[] args) {
        start(TestSpider.class);
    }
}
