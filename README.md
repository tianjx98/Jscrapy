# Jscrapy

* [创建爬虫类爬取数据](#创建爬虫类爬取数据)
* [提取数据](#提取数据)
  + [使用css选择器](#使用css选择器)
  + [使用xpath选择器](#使用xpath选择器)
* [存储爬取的数据](#存储爬取的数据)
  + [下载图片](#下载图片)
* [pixivApi使用](#pixivApi使用)

## 创建爬虫类爬取数据

创建一个类继承`me.tianjx98.jscrapy.core.impl.Spider`就可以快速创建一个爬虫类

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
      	// 从配置文件中读取多个爬虫类开始爬取(需要将爬虫类全类名写在配置文件中)
      	//start();
    }
	// 收到初始请求的响应后会调用此函数处理响应
    @Override
    public List<Request> parse(Response response) {
        System.out.println(response.getContent());

        // 对于html类型的响应,可以直接通过此方法来获取html标签
        // 获取出来的标签可能有多个
        Elements elements = response.css("css查询语句");
      
	// 如果不需要发出新的响应，可直接返回null，如果所有请求都处理完成，爬虫自动关闭
        // return null;
        // 发送单个请求
        return Request.builder("url", this)
                .addData("1", "2")//可以在请求对象中存储一些信息,之后取出来使用
                .addHeader("1", "2")//设置请求头
                .addBody("1", "2")//设置请求体
                .callback(this::parse)//设置回调函数，请求完成后会再次调用这个函数
                .build()//创建Request对象
                .asList();//将单个Request转换成list
    }

}
```

## 提取数据

### 使用css选择器

```java
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
```

### 使用xpath选择器

```java
NodeList list = response.xpath("/html/body/section/div/div[2]/ul/li/a");
for (int i = 0; i < list.getLength(); i++) {
    Node item = list.item(i);
    System.out.println(item.getTextContent());
}
```

## 存储爬取的数据

创建一个类继承`me.tianjx98.jscrapy.pipeline.Pipeline`，然后将这个类的全限定名加入到配置文件中的itemPipelines中，在爬虫里面调用`process(item)`方法，就会把item交给这些pipeline处理

可以参考`test.scraper.pixiv.PixivPipeline`

```java
public class PixivPipeline extends Pipeline {
    SequenceWriter writer;
  	// 在启动爬虫的时候这个方法会被调用一次
    @Override
    public void open() {
        // 设置json为易读的格式
        JSON.setPrettyFormat(true);
        // 从配置文件中获取存储路径
        String path = Setting.SETTINGS.getString("storePath");
        try {
            File file = new File(path + "pixiv.json");
            file.createNewFile();
            writer = JSON.getJsonWriter(new FileOutputStream(file));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
		
  	// 每次在Spider类中调用process(item)方法的时候，Item就会交给这个方法处理
    @Override
    public Item processItem(Item item, Spider spider) {
        try {
          	//将item以json格式写入文件中
            writer.write(item);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 返回item交给下一个pipeline处理
        return item;
    }
		
  	// 爬虫关闭时这个方法会被调用一次
    @Override
    public void close() {
        try {
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

`test.scraper.pixiv.PixivItem`，如果要将一个item以json格式写入文件中，必须要为所有属性生成get、set方法。下面的`@Data`、`@Builder`和` @JsonProperty`都是Lombok中的注解。

```java
@Data//自动生成get、set方法
@Builder// 自动生成建造者类
public class PixivItem extends Item {
    @JsonProperty("图片页面URL")//写入文件时会以这个名字替代pageUrl
    String pageUrl;
    @JsonProperty("图片编号")
    String pictureId;
    @JsonProperty("图片标题")
    String title;
    @JsonProperty("作者")
    String author;
    @JsonProperty("图片URL")
    String imageUrl;
    @JsonProperty("收藏数")
    int favorite;
    @JsonProperty("浏览数")
    int view;
}
```

### 下载图片

下载图片可以直接继承`me.tianjx98.jscrapy.pipeline.impl.ImageDownloadPipeline`

参考`test.scraper.pixiv.PixivImageDownloadPipeline`

```java
public class PixivImageDownloadPipeline extends ImageDownloadPipeline {
    @Override
    protected List<Request> getImageRequest(Item item, Spider spider) {
        if (item instanceof PixivItem) {
            // 从item中获取图片url然后生成请求
            PixivItem pixivItem = (PixivItem) item;
            return Request.builder(pixivItem.getImageUrl(), spider)
                    .addHeader("referer", pixivItem.getPageUrl())
                    .addData("filename", pixivItem.getAuthor() + "-" + pixivItem.getTitle())
                    .build()
                    .asList();
        }
        return null;
    }
		// 用这个方法生成文件名来存储图片
    @Override
    protected String filePath(Request request) {
        String imageUrl = request.getUrl().toString();
        return request.getDataMap().get("filename") + imageUrl.substring(imageUrl.lastIndexOf("."));
    }
}
```

## pixivApi使用

创建一个继承`me.tianjx98.jscrapy.utils.PixivApiSpider`就可以调用接口来获取信息

需要在配置文件中存储账号密码，然后会自动登录，之后才能使用api获取相关信息

```java
public class PixivApiSpiderTest extends PixivApiSpider {

    public static void main(String[] args) {
        start(PixivApiSpiderTest.class);
    }
		
  	// 登录成功后会调用此函数，参数为null
    @Override
    public List<Request> parse(Response response) {
        LinkedList<Request> requests = new LinkedList<>();
        // 获取图片详细信息
        requests.add(getIllustDetail("55841546", this::saveResult));
        // 获取排行榜
        requests.add(getIllustRanking(null, null, this::saveResult));
        // 获取某个图片的相关图片
        requests.add(getIllustRelated("55841546", this::saveResult));
        // 获取用户收藏的图片
        requests.add(getUserBookmarkIllusts("23513709", this::saveResult));
        // 获取用户详细信息
        requests.add(getUserDetail("23513709", this::saveResult));
        // 获取用户的作品
        requests.add(getUserIllusts("2188232", this::saveResult));
        return requests;
    }

    public List<Request> saveResult(Response response) {
        Charset charset = Charset.defaultCharset();
        try (FileOutputStream outputStream = new FileOutputStream(new File("pixivApiTest.json"), true)) {
            JsonNode josnNode = JSON.getJosnNode(response.getContent());
            outputStream.write(josnNode.toString().getBytes(charset));
            outputStream.write(",".getBytes(charset));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
```
