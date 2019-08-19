package me.tianjx98.Jscrapy.utils;

import com.fasterxml.jackson.databind.JsonNode;
import me.tianjx98.Jscrapy.core.Spider;
import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * @ClassName NewPixivSpider
 * @Description TODO
 * @Author tian
 * @Date 2019/8/19 16:21
 * @Version 1.0
 */
public class PixivApiSpider extends Spider {
    private static final Logger LOGGER = LoggerFactory.getLogger(PixivApiSpider.class);
    final String client_id = "bYGKuGVw91e0NMfPGp44euvGt59s";
    final String client_secret = "HP3RmkgAmEGro0gn1x9ioawQE8WMfvLXDz3ZqxpK";
    final String device_token = "af014441a5f1a3340952922adeba1c36";
    final String filter = "for_ios";
    //final String api_prefix = "https://public-api.secure.pixiv.net";
    final String api_prefix = "https://app-api.pixiv.net";
    private final String oauth_url = "https://oauth.secure.pixiv.net/auth/token";
    private String access_token;
    private String refresh_token;
    private Map<String, String> defaultHeaders = new HashMap<>();

    {
        name = "pixivApi";
        defaultHeaders.put("User-Agent", "PixivIOSApp/6.7.1 (iOS 10.3.1; iPhone8,1)");
        defaultHeaders.put("App-OS", "ios");
        defaultHeaders.put("App-OS-Version", "10.3.1");
        defaultHeaders.put("App-Version", "6.9.0");
    }

    public static void main(String[] args) {
        start(PixivApiSpider.class);
    }

    /**
     * 进行登录操作
     *
     * @return
     */
    @Override
    protected final List<Request> startRequests() {
        HashMap<String, String> params = new HashMap<>();
        params.put("client_id", client_id);
        params.put("client_secret", client_secret);
        params.put("grant_type", "password");
        params.put("device_token", device_token);
        try {
            params.put("username", SETTINGS.getString("username"));
            params.put("password", SETTINGS.getString("password"));
        } catch (Exception e) {
            LOGGER.info("获取 test.scraper.pixiv 账号密码失败, 未登录");
            // 直接通过parse发出初始请求
            return parse(null);
        }
        return Request.builder(oauth_url, this)
                .callback(this::login)
                .addBodies(params)
                .build()
                .asList();
    }

    private List<Request> login(Response response) {
        LOGGER.info(response.getContent());
        JsonNode loginResponse = JSON.getJosnNode(response.getContent()).get("response");
        try {
            access_token = "Bearer " + loginResponse.get("access_token").textValue();
            refresh_token = loginResponse.get("refresh_token").textValue();
        } catch (Exception e) {
            LOGGER.error("test.scraper.pixiv 登录失败");
            return null;
        }
        return parse(null);
    }

    /**
     * 获取用户详细信息(无需登录)
     *
     * @param userId   用户id
     * @param callback 回调函数
     * @return
     */
    public Request getUserDetail(String userId, Function<Response, List<Request>> callback) {
        return makeRequest("/v1/user/illusts?user_id=" + userId + "&filter=" + filter, callback, false);
    }

    /**
     * 获取用户作品列表(无需登录)
     *
     * @param userId   用户id
     * @param callback 回调函数
     * @return
     */
    public Request getUserIllusts(String userId, Function<Response, List<Request>> callback) {
        return makeRequest("/v1/user/illusts?user_id=" + userId + "&filter=" + filter, callback, false);
    }

    /**
     * 获取用户收藏作品列表(无需登录)
     *
     * @param userId   用户id
     * @param callback 回调函数
     * @return
     */
    public Request getUserBookmarkIllusts(String userId, Function<Response, List<Request>> callback) {
        return makeRequest("/v1/user/bookmarks/illust?user_id=" + userId + "&filter" + filter, callback, false);
    }

    /**
     * 获取图片的详细信息, 包含作者/图片标题/图片url/图片标签等信息(无需登录)
     *
     * @param illustId 图片的id
     * @param callback 获取图片信息成功后的回调函数
     * @return 返回一个获取图片详细信息的请求对象
     */
    public Request getIllustDetail(String illustId, Function<Response, List<Request>> callback) {
        return makeRequest("/v1/illust/detail?illust_id=" + illustId, callback, false);
    }

    /**
     * 获取图片的相关作品(无需登录)
     *
     * @param illustId 图片id
     * @param callback 回调函数
     * @return
     */
    public Request getIllustRelated(String illustId, Function<Response, List<Request>> callback) {
        return makeRequest("/v2/illust/related?illust_id=" + illustId + "&filter=" + filter, callback, false);
    }

    /**
     * 获取作品排行
     *
     * @param mode     mode: [day, week, month, day_male, day_female, week_original, week_rookie, day_manga]
     *                 mode(r18榜单需登录): [day_r18, day_male_r18, day_female_r18, week_r18, week_r18g]
     * @param date     日期, 格式为"2016-08-01"
     * @param callback 回调函数
     * @return
     */
    public Request getIllustRanking(String mode, String date, Function<Response, List<Request>> callback) {
        return makeRequest("/v1/illust/ranking?mode=" + (mode == null ? "day" : mode + "&filter=" + filter + (date == null ? "" : "&date=" + date)), callback, false);
    }

    /**
     * 搜索(无需登录)
     * 除了关键字和回调函数, 其他参数都可以为空
     *
     * @param keyWord  搜索关键字
     * @param mode     搜索模式
     *                 partial_match_for_tags  - 标签部分一致
     *                 exact_match_for_tags    - 标签完全一致
     *                 title_and_caption       - 标题说明文
     * @param sortBy   排序方式[date_desc, date_asc]
     * @param duration 时间[within_last_day, within_last_week, within_last_month]
     * @param callback 回调函数
     * @return
     */
    public Request searchIllust(String keyWord, String mode, String sortBy, String duration, Function<Response, List<Request>> callback) {
        String url = "/v1/search/illust?word=" + keyWord + "&mode=" + mode + "&sort=" + sortBy + "&filter=" + filter + (duration == null ? "" : "&duration=" + duration);
        return makeRequest(url, callback, false);
    }


    private List<Request> parseWork(Response response) {
        System.out.println(response.getContent());
        //JsonNode josnNode = JSON.getJosnNode(response.getContent());
        return null;
    }

    /**
     * 根据相对路径生成请求对象
     *
     * @param relativePath 相对路径,以/开始
     * @param callback     回调函数
     * @return 请求对象
     */
    private Request makeRequest(String relativePath, Function<Response, List<Request>> callback, boolean needAuth) {
        Request.Builder builder = Request.builder(api_prefix + relativePath, this)
                .addHeaders(defaultHeaders)
                .callback(callback);
        builder.addHeader("Authorization", access_token);
        return builder.build();
    }
}
