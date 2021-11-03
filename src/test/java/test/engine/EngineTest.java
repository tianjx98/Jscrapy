package test.engine;

import me.tianjx98.jscrapy.core.SpiderEngine;

/**
 * @author tianjx98
 * @date 2021/11/3 11:25
 */
public class EngineTest {
    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        SpiderEngine.start("test.engine");
        System.out.println("---------------启动完成");
    }
}
