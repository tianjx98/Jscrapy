package me.tianjx98.jscrapy.http;

import java.util.Map;
import java.util.function.Function;

import lombok.NonNull;
import me.tianjx98.jscrapy.core.Element;
import me.tianjx98.jscrapy.core.Spider;
import reactor.core.publisher.Flux;

/**
 * @author tianjx98
 * @date 2021/11/2 14:52
 */
public interface Request extends Element {

    /**
     * 校验url是否正确
     *
     * @return boolean
     */
    boolean validateUrl();

    /**
     * 获取请求的域名
     *
     * @return {@link String}
     */
    String getDomain();

    /**
     * 获取url
     *
     * @return {@link String}
     */
    String getUrl();

    /**
     * 是否需要判断url重复, 如果是, 并且url之前已经请求过, 调度器会丢弃此请求
     *
     * @return boolean
     */
    boolean isDoFilter();

    /**
     * 获取请求头
     *
     * @return {@link Map}<{@link String}, {@link String}>
     */
    Map<String, String> getHeaders();

    /**
     * 添加请求头
     *
     * @param key   请求头
     * @param value 值
     */
    void addHeader(String key, String value);

    /**
     * 获取产生此请求对象的爬虫类
     *
     * @return {@link Spider}
     */
    Spider getSpider();

    /**
     * 获取此请求对象的回调方法
     *
     * @return {@link Function}<{@link Response}, {@link Flux}<{@link Element}>>
     */
    Function<Response, Flux<Element>> getCallback();

    /**
     * 构建{@link okhttp3.Request}请求对象, 用于发送请求
     *
     * @return {@link Request}
     */
    okhttp3.Request buildRequest();

}
