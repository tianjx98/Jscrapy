package me.tianjx98.Jscrapy.utils;

import me.tianjx98.Jscrapy.core.Engine;
import me.tianjx98.Jscrapy.core.Spider;
import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.Response;
import me.tianjx98.Jscrapy.http.client.SyncHttpClient;
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
    private static Logger logger = LoggerFactory.getLogger(TestSpider.class);
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
        startUrls.add(LOGIN_PAGE);
    }

    @Override
    protected Function<Response, Object> startUrlsCallback() {
        return this::login;
    }

    /**
     * 登陆pixiv
     * @param response
     * @return
     */
    private Object login(Response response) {
        logger.trace(response.getRequest().getUrl().toString() + response.getResponse().getStatusLine());
        Elements select = response.select("#old-login > form > input[type=hidden]:nth-child(1)");
        // 获取登陆页面中一个隐藏的登陆参数
        String postKey = select.attr("value");

        HashMap<String, String> params = new HashMap<>();
        params.put("captcha", "");//添加参数
        params.put("g_recaptcha_response", "");
        params.put("pixiv_id", "973970940@qq.com");
        params.put("password", "973970940");
        params.put("post_key", postKey);
        params.put("source", "pc");
        params.put("ref", "wwwtop_accounts_index");
        params.put("return_to", "https://www.pixiv.net/");
        // 生成登陆请求
        return Request.builder(LOGIN_URL)
                .addBodies(params)
                .callback(this::loginStatus)// 该请求完成之后，会自动调用这个回调函数，参数是响应内容
                .build();
    }

    public Object loginStatus(Response response) {
        logger.info(response.getContent());
        return Request.builder(FAVORITE_URL)//获取收藏页面
                .callback(this::parse)
                .build();
    }

    @Override
    public Object parse(Response response) {
        System.out.println(response.getContent());//收藏页面内容
        //停止请求，如果所有请求都完成后，请求客户端会自动关闭
        //如果还需要继续请求，可以返回一个Request对象或Request[]
        return null;
    }

    public static void main(String[] args) {
        logger.info("启动TestSpider");
        start(TestSpider.class);
    }
}
