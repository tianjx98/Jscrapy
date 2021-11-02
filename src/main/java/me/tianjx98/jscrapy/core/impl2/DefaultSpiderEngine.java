package me.tianjx98.jscrapy.core.impl2;


import lombok.extern.log4j.Log4j2;
import me.tianjx98.jscrapy.config.JscrapyConfig;
import me.tianjx98.jscrapy.core.AbstractEngine;
import me.tianjx98.jscrapy.core.Engine;
import me.tianjx98.jscrapy.core.Spider;
import me.tianjx98.jscrapy.http.Request;
import reactor.core.publisher.Flux;

/**
 * @author tianjx98
 * @date 2021/11/1 13:05
 */
@Log4j2
public class DefaultSpiderEngine extends AbstractEngine {
    public DefaultSpiderEngine(JscrapyConfig config) {
        super(config);
    }

    @Override
    public Engine start() {
        final Flux<Request> defaultRequestFlux = Flux.fromIterable(spiders).flatMap(Spider::startRequests);

        for (Spider spider : spiders) {
            Flux.from(spider.startRequests());
        }
        return this;
    }

    @Override
    public void pause() {

    }

    @Override
    public void stop() {

    }
}
