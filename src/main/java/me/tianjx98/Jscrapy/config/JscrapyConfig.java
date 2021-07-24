package me.tianjx98.Jscrapy.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

/**
 * @author 18872653103
 * @date 2021/7/19 12:45
 */
@Component
@ConfigurationProperties(prefix = "jscrapy")
@Data
public class JscrapyConfig {

    /**
     * 最大线程数
     */
    Integer maxThreadNum = 2;
    /**
     * 请求超时时间, 单位为s
     */
    Double connectionTimeout = 30.0;
    /**
     * 并发请求的最大值, 默认最多可以同时发出16个请求
     */
    Integer concurrentRequests = 16;
    /**
     * 每一个域名下的并发请求最大值
     */
    Integer concurrentRequestsPerDomain = 8;
    /**
     * 随机下载延迟, 对于同一个域名下的请求, 两次请求之间会产生一个0-randomDownloadDelay(s)的延迟
     */
    Double randomDownloadDelay = -1.0;
    /**
     * 设置代理，如果需要翻墙就要使用代理才能正常访问
     */
    Proxy proxy;

    @Data
    public static class Proxy {
        /**
         * 是否启用
         */
        boolean enable = false;
        /**
         * 代理ip
         */
        String host;
        /**
         * 端口
         */
        Integer port;
    }

    /**
     * 爬虫深度限制，如果请求的深度超过此限制，请求将会被丢弃
     */
    Integer depthLimit = 0;
}
