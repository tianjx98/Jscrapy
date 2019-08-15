# Jscrapy

## 爬虫类

创建一个类继承`me.tianjx98.Jscrapy.core.Spider`就可以快速创建一个爬虫类

简单使用可以参考`test.demo.TestSpider`

## 存储爬取的数据

创建一个类继承`me.tianjx98.Jscrapy.pipeline.Pipeline`，然后将这个类的全限定名加入到配置文件中的itemPipelines中，在爬虫里面调用process(item)方法，就会把item交给这些

可以参考`test.pixiv.PixivPipeline`

### 下载图片

下载图片可以直接继承`me.tianjx98.Jscrapy.pipeline.impl.ImageDownloadPipeline`

参考`test.pixiv.PixivImageDownloadPipeline`