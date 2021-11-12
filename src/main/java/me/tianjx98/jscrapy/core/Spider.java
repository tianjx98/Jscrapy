package me.tianjx98.jscrapy.core;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import me.tianjx98.jscrapy.http.Request;
import me.tianjx98.jscrapy.http.Response;
import reactor.core.publisher.Flux;

/**
 * 爬虫类接口, 爬虫引擎启动时会获取这个接口所有的实现类, 获取到起始请求, 发送请求
 * 
 * @author tianjx98
 * @date 2021/7/26 19:25
 */
public interface Spider {

    /**
     * 获取爬虫名称
     * 
     * @return 爬虫名称
     */
    String getName();

    /**
     * 在{@link Spider#startRequests()}会调用此方法, 将初始URL转换为请求对象
     * 
     * @return 初始请求url
     */
    Flux<String> startUrls();

    /**
     * 
     * @return
     */
    Flux<Request> startRequests();

    default Function<Response, Flux<Element>> startCallback() {
        return this::parse;
    }

    Flux<Element> parse(Response response);

    default Map<String, String> getDefaultHeaders() {
        return Collections.emptyMap();
    }
}
