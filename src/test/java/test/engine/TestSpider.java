package test.engine;

import java.util.concurrent.atomic.AtomicInteger;

import me.tianjx98.jscrapy.core.AbstractSpider;
import me.tianjx98.jscrapy.core.Element;
import me.tianjx98.jscrapy.core.annotation.ScraperElement;
import me.tianjx98.jscrapy.http.Response;
import reactor.core.publisher.Flux;

/**
 * @author tianjx98
 * @date 2021/11/3 13:04
 */
@ScraperElement
public class TestSpider extends AbstractSpider {
    @Override
    public String getName() {
        return "testSpider";
    }

    @Override
    public Flux<String> startUrls() {
        return Flux.range(1, 10).map(i -> String.format("http://dev.hzero.org:8080/hutl/v1/test/public/%s", i));
    }

    private AtomicInteger i = new AtomicInteger(0);

    @Override
    public Flux<Element> parse(Response response) {
        final String body = response.getBody();
        System.out.println(i.addAndGet(Integer.parseInt(body)));
        return Flux.just(new TestItem(body));
    }


}
