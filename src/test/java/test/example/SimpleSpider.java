package test.example;

import lombok.extern.log4j.Log4j2;
import cn.tianjx98.jscrapy.core.Element;
import cn.tianjx98.jscrapy.core.Spider;
import cn.tianjx98.jscrapy.core.SpiderEngine;
import cn.tianjx98.jscrapy.core.annotation.ScraperElement;
import cn.tianjx98.jscrapy.http.Response;
import reactor.core.publisher.Flux;

@ScraperElement
@Log4j2
public class SimpleSpider implements Spider {
    @Override
    public String getName() {
        return "simpleSpider";
    }

    @Override
    public Flux<String> startUrls() {
        return Flux.just("https://www.baidu.com");
    }

    @Override
    public Flux<Element> parse(Response response) {
        log.info("解析响应: {}", response);
        return Flux.just(new SimpleItem(response.getBody()));
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        SpiderEngine.start(SimpleSpider.class);
    }
}
