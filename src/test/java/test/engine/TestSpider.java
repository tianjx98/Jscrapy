package test.engine;

import me.tianjx98.jscrapy.core.AbstractSpider;
import me.tianjx98.jscrapy.core.annotation.Spider;
import me.tianjx98.jscrapy.http.Request;
import me.tianjx98.jscrapy.http.Response;
import reactor.core.publisher.Flux;

/**
 * @author tianjx98
 * @date 2021/11/3 13:04
 */
@Spider
public class TestSpider extends AbstractSpider {
    @Override
    public String getName() {
        return "testSpider";
    }

    @Override
    public Flux<String> startUrls() {
        return Flux.range(0, 20).map(i -> String.format("http://dev.hzero.org:8080/hutl/v1/test/public/%s", i));
    }

    @Override
    public Flux<Request> parse(Response response) {
        final String body = response.getBody();
        System.out.println(body);
        return Flux.empty();
    }
}
