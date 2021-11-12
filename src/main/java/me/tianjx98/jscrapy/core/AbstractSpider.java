package me.tianjx98.jscrapy.core;

import lombok.extern.log4j.Log4j2;
import me.tianjx98.jscrapy.http.Request;
import me.tianjx98.jscrapy.http.impl.DefaultRequest;
import reactor.core.publisher.Flux;

/**
 * @author tianjx98
 * @date 2021/11/2 13:09
 */
@Log4j2
public abstract class AbstractSpider implements Spider {

    @Override
    public Flux<String> startUrls() {
        return Flux.empty();
    }

    @Override
    public Flux<Request> startRequests() {
        return startUrls().map(url -> new DefaultRequest(this, url, startCallback()));
    }
}
