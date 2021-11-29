# Jscrapy



# 快速使用
添加依赖
```xml
<dependency>
    <groupId>cn.tianjx98</groupId>
    <artifactId>jscrapy</artifactId>
    <version>1.0.0</version>
</dependency>
```

```java
import lombok.extern.log4j.Log4j2;
import cn.tianjx98.jscrapy.core.Element;
import cn.tianjx98.jscrapy.core.Spider;
import cn.tianjx98.jscrapy.core.SpiderEngine;
import cn.tianjx98.jscrapy.core.annotation.ScraperElement;
import cn.tianjx98.jscrapy.http.Response;
import reactor.core.publisher.Flux;

@ScraperElement
@Log4j2
public class SimpleSpider implements Spider {
    @Override
    public String getName() {
        return "simpleSpider";
    }

    @Override
    public Flux<String> startUrls() {
        return Flux.just("https://www.baidu.com");
    }

    @Override
    public Flux<Element> parse(Response response) {
        System.out.println(response.getBody());
        return Flux.empty();
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        SpiderEngine.start(SimpleSpider.class);
    }
}

```

## 数据持久化

1. 定义Item

```java

@Data
public class SimpleItem extends Item {
    String body;

    public SimpleItem(String body) {
        this.body = body;
    }
}
```

2. 定义Pipeline

```java
/**
 * 可以使用Pipeline完成数据持久化的逻辑<br>
 * 采用了过滤器设计模式, 可以定义多个Pipeline对数据进行处理<br>
 * 注意: 如果模板参数为SimpleItem, 那么这个Pipeline就只会处理类型为SimpleItem的Item
 *
 * @author tianjx98
 * @date 2021/11/4 15:06
 */
@Log4j2
@ScraperElement
@Order(1)
public class SimplePipeline implements Pipeline<SimpleItem> {

    @Override
    public void open() {
        // 启动时执行此方法
    }

    @Override
    public SimpleItem processItem(SimpleItem item, Spider spider) {
        log.info("SimplePipeline: " + item);
        // 保存数据
        return item;
    }

    @Override
    public void close() {
        // 结束时执行此方法
    }
}
```

3. 启动爬虫

```java
@ScraperElement
@Log4j2
public class SimpleSpider implements Spider {
    @Override
    public String getName() {
        return "simpleSpider";
    }

    @Override
    public Flux<String> startUrls() {
        return Flux.just("https://www.baidu.com");
    }

    @Override
    public Flux<Element> parse(Response response) {
        log.info("解析响应: {}", response);
        return Flux.just(new SimpleItem(response.getBody()));
    }

    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        SpiderEngine.start(SimpleSpider.class);
    }
}
```

