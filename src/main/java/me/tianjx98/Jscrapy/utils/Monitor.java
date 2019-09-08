package me.tianjx98.Jscrapy.utils;

import me.tianjx98.Jscrapy.core.BasicEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @ClassName Monitor
 * @Description TODO
 * @Author tianjx98
 * @Date 2019-08-25 16:43
 */
public class Monitor extends Thread {
    private static final Logger LOGGER = LoggerFactory.getLogger(Monitor.class);
    private final BasicEngine engine;

    public Monitor(BasicEngine engine) {
        this.engine = engine;
        setDaemon(true);
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(3000);
                printStatus();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void printStatus() {
        LOGGER.info("待发送请求数: " + engine.getScheduler().size());
        LOGGER.info("已发送请求(未响应): " + engine.getDownloader().getSize());
    }
}
