package me.tianjx98.jscrapy.core;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Optional;

import lombok.extern.log4j.Log4j2;
import me.tianjx98.jscrapy.core.Spider;
import me.tianjx98.jscrapy.http.Request;
import me.tianjx98.jscrapy.http.Response;
import me.tianjx98.jscrapy.http.impl.DefaultRequest;
import org.graalvm.compiler.nodes.calc.IntegerDivRemNode;
import reactor.core.publisher.Flux;

/**
 * @author tianjx98
 * @date 2021/11/2 13:09
 */
@Log4j2
public abstract class AbstractSpider implements Spider {
    protected final String name;

    protected AbstractSpider(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Flux<String> startUrls() {
        return Flux.just();
    }

    @Override
    public Flux<Request> startRequests() {
        return startUrls().map(this::parseRequest).filter(Optional::isPresent).map(Optional::get);
    }

    private Optional<Request> parseRequest(String url) {
        try {
            return Optional.of(new DefaultRequest(this, new URL(url), startCallback()));
        } catch (MalformedURLException e) {
            log.error("url格式错误", e);
            return Optional.empty();
        }
    }
}
