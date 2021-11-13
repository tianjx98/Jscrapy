package me.tianjx98.jscrapy.core;

import java.util.Collections;
import java.util.Map;
import java.util.function.Function;

import me.tianjx98.jscrapy.http.Request;
import me.tianjx98.jscrapy.http.Response;
import me.tianjx98.jscrapy.http.impl.DefaultRequest;
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
     * 在{@link Spider#startRequests()}会调用此方法, 将初始URL转换为请求对象<br>
     * <p>
     * 例子:<br>
     * {@code Flux.just("https://www.baidu.com/");}
     *
     * @return 初始请求url
     */
    Flux<String> startUrls();

    /**
     * 初始请求, 爬虫引擎启动时会调用此方法生成初始的请求对象, 如果只是简单的get请求, 直接覆盖{@link Spider#startUrls()}方法即可
     *
     * @return {@link Flux}<{@link Request}>
     */
    default Flux<Request> startRequests() {
        return startUrls().map(url -> new DefaultRequest(this, url, startCallback()));
    }

    /**
     * 初始请求的回调方法默认为{@link Spider#parse(Response)}
     *
     * @return {@link Function}<{@link Response}, {@link Flux}<{@link Element}>>
     */
    default Function<Response, Flux<Element>> startCallback() {
        return this::parse;
    }

    /**
     * 初始请求的默认回调方法, 当请求完成后, 会默认调用此方法处理响应结果, 并根据返回值类型进行不同的处理
     *
     * @param response 响应对象, 保护请求状态, 响应报文等信息
     * @return {@link Flux}<{@link Element}> 目前支持两种返回元素<br>
     * 1.{@link Request} 请求对象, 引擎会将请求放入调度器稍后请求, 并在收到响应后通过{@link Request#getCallback()}获取回调方法进行回调<br>
     * 2.{@link me.tianjx98.jscrapy.pipeline.Item} 数据对象, 创建一个类继承此类, 解析响应后将数据封装到子类中然后返回, 引擎会将这些对象交给{@link me.tianjx98.jscrapy.pipeline.Pipeline}的实现类处理
     */
    Flux<Element> parse(Response response);

    /**
     * 设置默认请求头, 此爬虫对象产生的请求都会附带此方法设置的默认请求头
     *
     * @return {@link Map}<{@link String}, {@link String}> 请求头键值对
     */
    default Map<String, String> getDefaultHeaders() {
        return Collections.emptyMap();
    }
}
