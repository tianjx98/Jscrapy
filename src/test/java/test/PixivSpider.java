package test;

import com.fasterxml.jackson.databind.JsonNode;
import me.tianjx98.Jscrapy.core.Spider;
import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.Response;
import me.tianjx98.Jscrapy.utils.JSON;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.function.Function;
import java.util.regex.Pattern;

/**
 * @ClassName PixivSpider
 * @Description TODO
 * @Author tian
 * @Date 2019/7/21 12:14
 * @Version 1.0
 */
public class PixivSpider extends Spider {
    private static final Logger LOGGER = LoggerFactory.getLogger(PixivSpider.class);
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

    private Pattern pattern = Pattern.compile("\\d+");

    public static void main(String[] args) {
        LOGGER.info("启动TestSpider");
        start();
    }

    /**
     * 登陆pixiv
     *
     * @param response
     * @return
     */
    private Object login(Response response) {
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
                .callback(this::loginStatus)// 该请求完成之后，会自动调用这个回调函数，参数是响应内容
                .build();
    }

    private Object loginStatus(Response response) {
        LOGGER.info(response.getContent());
        return Request.builder(FAVORITE_URL, this)//获取收藏页面
                .callback(this::parseFavPage)
                .build();
    }

    private Object parseFavPage(Response response) {
        LOGGER.info("处理收藏页面");
        // 获取所有收藏图片的相对路径
        List<String> hrefs = response.select("#wrapper > div.layout-a > div.layout-column-2 > div._unit.manage-unit > form > div.display_editable_works > ul > li > a.work._work").eachAttr("href");
        // 生成Request对象
        List<Request> follow = response.follow(hrefs, this::parse);

        List<String> pages = response.select("#wrapper > div.layout-a > div.layout-column-2 > div._unit.manage-unit > form > nav > div > ul > li > a").eachAttr("href");
        follow.addAll(response.follow(pages, this::parseFavPage));
        return follow;
    }

    /**
     * 解析图片页面
     *
     * @param response 响应内容
     * @return
     */
    @Override
    public Object parse(Response response) {
        LOGGER.info("解析图片页面");
        String text = response.getContent();
        String pageUrl = response.getRequest().getUrl().toString();
        int lastEq = response.getRequest().getUrl().toString().lastIndexOf("=") + 1;
        String pictureId = pageUrl.substring(lastEq);
        String substring = text.substring(text.indexOf("illust: ") + 8, text.indexOf(",user: {"));
        JsonNode node = JSON.getJosnNode(substring).get(pictureId);
        // 获取图片的点赞数、喜欢数、浏览次数
        // 喜欢(收藏数)
        int favorite = node.get("bookmarkCount").intValue();
        // 点赞
        int upvote = node.get("likeCount").intValue();
        // 评论数
        int comment = node.get("commentCount").intValue();
        // 浏览次数
        int view = node.get("viewCount").intValue();
        // 获取图片的url等信息
        String url = node.get("urls").get("original").textValue();
        String author = node.get("userName").textValue();
        String title = node.get("illustTitle").textValue();
        process(PixivItem.builder()
                .favorite(favorite)
                .upvote(upvote)
                .comment(comment)
                .view(view)
                .pageUrl(pageUrl)
                .imageUrl(url)
                .author(author)
                .pictureId(pictureId)
                .title(title)
                .build());
        return null;
    }
}
