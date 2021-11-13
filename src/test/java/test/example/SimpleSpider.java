package test.example;

import me.tianjx98.jscrapy.core.Element;
import me.tianjx98.jscrapy.core.Spider;
import me.tianjx98.jscrapy.core.SpiderEngine;
import me.tianjx98.jscrapy.core.annotation.ScraperElement;
import me.tianjx98.jscrapy.http.Response;
import reactor.core.publisher.Flux;

@ScraperElement
public class SimpleSpider implements Spider {
    @Override
    public String getName() {
        return "demoSpider";
    }

    @Override
    public Flux<String> startUrls() {
        return Flux.just("https://www.baidu.com");
    }

    @Override
    public Flux<Element> parse(Response response) {
        System.out.println(response.getBody());
        return null;
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        SpiderEngine.start(SimpleSpider.class);
    }
}
