package me.tianjx98.jscrapy.http;

/**
 * @author tianjx98
 * @date 2021/11/8 12:09
 */
@FunctionalInterface
public interface RequestBuilder {
    okhttp3.Request.Builder apply(okhttp3.Request.Builder builder, Request request);
}
