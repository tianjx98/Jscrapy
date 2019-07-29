package me.tianjx98.Jscrapy.core;

import com.typesafe.config.Config;
import me.tianjx98.Jscrapy.utils.Setting;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 主要用于下载各种请求
 * 包含下载处理器，下载器中间件
 *
 * @ClassName Downloader
 * @Author tian
 * @Date 2019/7/22 13:47
 * @Version 1.0
 */
public class Downloader {

    private static final Config SETTINGS = Setting.SETTINGS;
    private static final Logger LOGGER = LoggerFactory.getLogger(Downloader.class);

}
