package me.tianjx98.Jscrapy.utils;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * 配置文件的单例类
 * @ClassName Setting
 * @Description 配置文件的单例类
 * @Author tian
 * @Date 2019/7/20 21:05
 * @Version 1.0
 */
public class Setting {
    public static final Config SETTINGS = ConfigFactory.load("settings.conf")//读取用户配置文件,会覆盖默认配置文件
            .withFallback(ConfigFactory.load("default-settings.conf"));//读取默认配置文件
}
