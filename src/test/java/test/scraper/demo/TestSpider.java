package test.scraper.demo;

import me.tianjx98.Jscrapy.core.impl.Spider;
import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.Response;

import java.util.List;

/**
 * @ClassName TestSpider
 * @Description TODO
 * @Author tianjx98
 * @Date 2019-08-15 16:40
 */
public class TestSpider extends Spider {

    {
        // 设置爬虫名称，必须设置
        name = "test";
        // 设置起始url，最开始会根据这些url发送请求，请求完成后会调用this::parse函数处理响应
        // 也可以重载startUrlsCallback()来修改默认的回调函数
        //startUrls.add("https://www.baidu.com");
    }

    public static void main(String[] args) {
        start(TestSpider.class);
    }

    @Override
    public List<Request> parse(Response response) {
        System.out.println("content+" + response.getContent());


        /**
         * 使用xpath获取元素
         */

        return null;
        /**
         // 对于html类型的响应,可以直接通过此方法来获取html标签
         // 获取出来的标签可能有多个
         Elements elements = response.css("css查询语句");
         // 获取所有标签的href属性
         List<String> href = elements.eachAttr("href");
         // 获取里面的第一个标签
         Element first = elements.first();
         // 获取标签的属性
         String attr = first.attr("attr");
         // 获取标签中的文本信息
         first.text();

         // 发送更多的请求, 对于返回的请求, 爬虫稍后会自动发送请求,然后调用你设置的回调函数来处理

         // 根据本页面a标签中的相对路径发送请求, 这个方法会自动在请求头中加入referer属性
         //return response.follow(href, this::parse);
         // 如果需要添加一些参数可以使用这个方法
         //return response.follow(href, builder -> {
         //    builder.addHeader("1", "2")
         //            .callback(this::parse);
         //});

         // 发送单个请求
         return Request.builder("url", this)
         .addData("1", "2")//可以在请求对象中存储一些信息,之后取出来使用
         .addHeader("1", "2")//设置请求头
         .addBody("1", "2")//设置请求体
         .callback(this::parse)//设置回调函数
         .build()
         .asList();*/
    }

}
