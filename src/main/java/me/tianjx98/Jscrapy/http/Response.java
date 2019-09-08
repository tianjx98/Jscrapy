package me.tianjx98.Jscrapy.http;

import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;

public class Response {
    private static Logger LOGGER = LoggerFactory.getLogger(Response.class);
    /**
     * 请求类，包含请求url，请求头，实体内容等信息
     */
    private Request request;
    /**
     * 响应类
     */
    private HttpResponse response;
    /**
     * 响应体
     */
    private String content;
    /**
     * 通过Jsoup解析得到的document对象
     */
    private Document document;

    private boolean isHtml = false;

    public Response() {
    }

    public Response(Request request, HttpResponse response) {
        this.request = request;
        this.response = response;
        String contentType = response.getFirstHeader("Content-Type").getValue();
        isHtml = contentType.startsWith("text/html");
        try {
            // 如果是文本类型就将文本保存在String里面
            if (contentType.startsWith("text") || contentType.startsWith("application/json")) {
                // TODO: charset问题还没解决, 如果content-type里面没有charset就会乱码
                content = EntityUtils.toString(response.getEntity());
            }
            // 如果是html文档就解析
            if (isHtml) {
                this.document = Jsoup.parse(content);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 可以直接使用谷歌浏览器的控制台中，复制标签的css选择语法
     *
     * @param cssQuery css查询表达式
     * @return 返回被选择的元素，如果响应类型不是html，则返回null
     */
    public Elements select(String cssQuery) {
        if (!isHtml) {
            return null;
        }
        Elements select = document.select(cssQuery);
        return select;
    }

    private Request.Builder followRequestBuilder(String relativePath, Function<Response, List<Request>> callback) {
        try {
            String curUrl = request.getUrl().toString();
            Request.Builder builder = relativePath.startsWith("?") && !curUrl.contains("?") ?
                    // 如果相对路径以?开头，只需要把参数拼接到url后面
                    Request.builder(curUrl + relativePath, request.getSpider())
                    //通过URL对象的构造函数来拼接url
                    : Request.builder(new URL(request.getUrl(), relativePath), request.getSpider());
            return builder
                    .addHeader("Referer", request.getUrl().toString())
                    .callback(callback);
        } catch (MalformedURLException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 根据当前响应结合相对路径形成新的请求
     *
     * @param relativePath 相对路径
     * @param callback     请求的回调函数
     * @return
     */
    public Request follow(String relativePath, Function<Response, List<Request>> callback) {
        return followRequestBuilder(relativePath, callback).build();
    }

    /**
     * 根据当前响应结合相对路径形成新的请求
     * 需要在Consumer中设置回调函数
     *
     * @param relativePath 相对路径
     * @param consumer     可以在形成请求前对请求设定回调函数等值
     * @return 返回请求的集合
     */
    public Request follow(String relativePath, Consumer<Request.Builder> consumer) {
        Objects.requireNonNull(consumer, "consumer");
        Request.Builder builder = followRequestBuilder(relativePath, null);
        consumer.accept(builder);
        return builder.build();
    }

    /**
     * 根据当前响应结合相对路径形成新的请求
     *
     * @param relativePaths 相对路径集合
     * @param callback      回调函数
     * @return 返回请求对象
     */
    public List<Request> follow(List<String> relativePaths, Function<Response, List<Request>> callback) {
        ArrayList<Request> requests = new ArrayList<>();
        for (String url : relativePaths) {
            requests.add(followRequestBuilder(url, callback).build());
        }
        return requests;
    }

    /**
     * 根据当前响应结合相对路径形成新的请求
     * 需要在Consumer中设置回调函数
     *
     * @param relativePaths 相对路径集合
     * @param consumer      可以在形成请求前对请求设定回调函数等值
     * @return 返回请求对象的集合
     */
    public List<Request> follow(List<String> relativePaths, Consumer<Request.Builder> consumer) {
        Objects.requireNonNull(consumer, "consumer");
        ArrayList<Request> requests = new ArrayList<>();
        for (String url : relativePaths) {
            Request.Builder builder = followRequestBuilder(url, null);
            consumer.accept(builder);
            requests.add(builder.build());
        }
        return requests;
    }

    /**
     * 获取原生的响应对象, 如果需要获取响应头等信息可以通过此对象获取
     *
     * @return 原生响应对象
     */
    public HttpResponse getResponse() {
        return response;
    }

    /**
     * 获取该响应的请求对象
     *
     * @return 响应的请求对象
     */
    public Request getRequest() {
        return request;
    }

    /**
     * 获取响应内容的字符串
     *
     * @return 如果响应内容为文本, 就返回一个字符串, 否则返回null
     */
    public String getContent() {
        return content;
    }
}
