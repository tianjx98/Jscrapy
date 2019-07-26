package test;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class Main {
    private static Logger logger = LoggerFactory.getLogger(Main.class);
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

    public static void main(String[] args) throws IOException {
        //Response page = AsyncHttpClient.post(LOGIN_PAGE);
        //System.out.println(page.getContent());
        //Elements select = page.select("#old-login > form > input[type=hidden]:nth-child(1)");
        //String postKey = select.attr("value");

        //HashMap<String, String> params = new HashMap<>();
        //params.put("captcha", "");//添加参数
        //params.put("g_recaptcha_response", "");
        //params.put("pixiv_id", "973970940@qq.com");
        //params.put("password", "973970940");
        //params.put("post_key", postKey);
        //params.put("source", "pc");
        //params.put("ref", "wwwtop_accounts_index");
        //params.put("return_to", "https://www.pixiv.net/");
        //Response homePage = SyncHttpClient.post(LOGIN_URL, params);
        //Response response = SyncHttpClient.get(FAVORITE_URL);
        //System.out.println(response.getContent());

        //AsyncHttpClient.close();
    }
}
