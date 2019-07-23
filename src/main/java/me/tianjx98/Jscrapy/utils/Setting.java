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
    // TODO: 读取用户的配置文件来覆盖默认配置文件
    public static final Config SETTINGS = ConfigFactory.load("settings.conf");
}
