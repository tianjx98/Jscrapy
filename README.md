# Jscrapy

## 爬虫类

创建一个类继承`me.tianjx98.Jscrapy.core.Spider`就可以快速创建一个爬虫类

简单使用可以参考`test.scraper.demo.TestSpider`

```java
public class TestSpider extends Spider {

    {
        // 设置爬虫名称，必须设置
        name = "test";
        // 设置起始url，最开始会根据这些url发送请求，请求完成后会调用this::parse函数处理响应
        // 也可以重载startUrlsCallback()来修改默认的回调函数
        startUrls.add("https://www.baidu.com");
    }
		// 启动爬虫
    public static void main(String[] args) {
      	// 启动单个爬虫
        start(TestSpider.class);
      	// 从配置文件中读取多个爬虫类开始爬取
      	//start();
    }
		// 收到初始请求的响应后会调用此函数处理响应
    @Override
    public List<Request> parse(Response response) {
        System.out.println(response.getContent());

        // 对于html类型的响应,可以直接通过此方法来获取html标签
        // 获取出来的标签可能有多个
        Elements elements = response.select("css查询语句");
      
				// 如果不需要发出新的响应，可直接返回null，如果所有请求都处理完成，爬虫自动关闭
        // return null;
        // 发送单个请求
        return Request.builder("url", this)
                .addData("1", "2")//可以在请求对象中存储一些信息,之后取出来使用
                .addHeader("1", "2")//设置请求头
                .addBody("1", "2")//设置请求体
                .callback(this::parse)//设置回调函数，请求完成后会再次调用这个函数
                .build()//创建Request对象
                .asList();//将Request转换成list
    }

}
```



## 存储爬取的数据

创建一个类继承`me.tianjx98.Jscrapy.pipeline.Pipeline`，然后将这个类的全限定名加入到配置文件中的itemPipelines中，在爬虫里面调用`process(item)`方法，就会把item交给这些pipeline处理

可以参考`test.scraper.pixiv.PixivPipeline`

### 下载图片

下载图片可以直接继承`me.tianjx98.Jscrapy.pipeline.impl.ImageDownloadPipeline`

参考`test.scraper.pixiv.PixivImageDownloadPipeline`