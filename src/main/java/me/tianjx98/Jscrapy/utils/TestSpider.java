package me.tianjx98.Jscrapy.utils;

import me.tianjx98.Jscrapy.core.Spider;
import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.Response;
import me.tianjx98.Jscrapy.pipeline.Item;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.function.Function;

/**
 *
 * @ClassName TestSpider
 * @Description TODO
 * @Author tian
 * @Date 2019/7/21 12:14
 * @Version 1.0
 */
public class TestSpider extends Spider {
    private static final Logger LOGGER = LoggerFactory.getLogger(TestSpider.class);
    /**
     * 登陆页面url
     * 登陆之前需要请求此页面获取postKey用作登陆参数
     */
    private static final String LOGIN_PAGE = "https://accounts.pixiv.net/login?lang=zh&source=pc&view_type=page&ref=wwwtop_accounts_index";
    /**
     * 登陆url
     * 登陆时需要请求此url
     */
    private static final String LOGIN_URL = "https://accounts.pixiv.net/api/login?lang=zh";
    /**
     * 收藏页面url
     * 用于访问收藏页面
     */
    private static final String FAVORITE_URL = "https://www.pixiv.net/bookmark.php";

    {
        name = "pixiv";
        startUrls.add(LOGIN_PAGE);
    }

    @Override
    protected Function<Response, Object> startUrlsCallback() {
        return this::login;
    }

    public static void main(String[] args) {
        LOGGER.info("启动TestSpider");
        start();
    }

    /**
     * 登陆pixiv
     * @param response
     * @return
     */
    private Object login(Response response) {
        LOGGER.trace(response.getRequest().getUrl().toString() + response.getResponse().getStatusLine());
        // 可以直接在谷歌浏览器控制台复制某个标签的css选择器语法在这里用
        Elements tag = response.select("#old-login > form > input[type=hidden]:nth-child(1)");
        // 获取登陆页面中一个隐藏的登陆参数
        String postKey = tag.attr("value");
        // 获取配置文件里面的用户名和密码
        String username = settings.getString("username");
        String password = settings.getString("password");
        HashMap<String, String> params = new HashMap<>();
        params.put("captcha", "");//添加参数
        params.put("g_recaptcha_response", "");
        params.put("pixiv_id", username);
        params.put("password", password);
        params.put("post_key", postKey);
        params.put("source", "pc");
        params.put("ref", "wwwtop_accounts_index");
        params.put("return_to", "https://www.pixiv.net/");
        // 生成登陆请求
        return Request.builder(LOGIN_URL, this)
                .addBodies(params)
                //.callback(this::loginStatus)// 该请求完成之后，会自动调用这个回调函数，参数是响应内容
                .build();
    }

    public Object loginStatus(Response response) {
        LOGGER.info(response.getContent());
        return Request.builder(FAVORITE_URL, this)//获取收藏页面
                .callback(this::parse)
                .build();
    }

    @Override
    public Object parse(Response response) {
        //System.out.println(response.getContent());//收藏页面内容
        process(new Item());
        //停止请求，如果所有请求都完成后，请求客户端会自动关闭
        //如果还需要继续请求，可以返回一个Request对象或Request[]
        return null;
    }
}
