package me.tianjx98.jscrapy.utils;

import me.tianjx98.jscrapy.config.JscrapyConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @author 18872653103
 * @date 2021/7/24 15:10
 */
@Component
public class TestRunner implements ApplicationRunner {
    @Autowired
    JscrapyConfig config;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println(config);
    }
}
