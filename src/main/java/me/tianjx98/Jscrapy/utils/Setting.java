package me.tianjx98.Jscrapy.utils;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

/**
 * @ClassName Setting
 * @Description 配置文件的单例类
 * @Author tian
 * @Date 2019/7/20 21:05
 * @Version 1.0
 */
public class Setting {
    public static final Config settings = ConfigFactory.load("settings.conf");
}
