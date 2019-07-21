package me.tianjx98.Jscrapy.utils;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigObject;
import com.typesafe.config.ConfigValue;
import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.Response;
import me.tianjx98.Jscrapy.http.client.AsyncHttpClient;

import java.io.IOException;
import java.net.*;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;


public class Test {

    Test() throws Exception {
        throw new Exception();
    }
    public static void main(String[] args) throws Exception {
        Test test = new Test();
        System.out.println(test);

        //Object strings = new String[]{"1","2"};
        //System.out.println(strings instanceof String[]);
        //Config config = ConfigFactory.load("settings.conf");
        //ConfigObject headers = config.getObject("defaultHeaders");
        //for (Map.Entry<String, ConfigValue> entry : headers.entrySet()) {
        //    System.out.println(entry.getKey()+":"+entry.getValue().unwrapped().toString());
        //}
        //ConfigObject proxy = config.getObject("proxy");
        //System.out.println(proxy.toConfig().getString("host"));
        //System.out.println(proxy.toConfig().getInt("port"));

        //URL url = new URL("https://www.bilibili.com");
        //System.out.println(url.toURI());
        //
        Request request = Request.builder("https://www.bilibili.com")
                .addBody("body", "value")
                .addBody("name","val")
                .addHeader("set-cookie", "value")
                .addHeader("User-Agent","213")
                .build();
        //System.out.println(request);
    }
}
