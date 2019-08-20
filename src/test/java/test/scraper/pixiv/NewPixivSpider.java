package test.scraper.pixiv;

import com.fasterxml.jackson.databind.JsonNode;
import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.Response;
import me.tianjx98.Jscrapy.utils.JSON;
import me.tianjx98.Jscrapy.utils.PixivApiSpider;

import java.util.List;

/**
 * @ClassName NewPixivSpider
 * @Description TODO
 * @Author tianjx98
 * @Date 2019-08-20 09:22
 */
public class NewPixivSpider extends PixivApiSpider {
    {
        name = "pixiv";
    }

    public static void main(String[] args) {
        start(NewPixivSpider.class);
    }

    @Override
    public List<Request> parse(Response response) {
        return getUserBookmarkIllusts("23513709", this::parseUserBookmark).asList();
    }

    private List<Request> parseUserBookmark(Response response) {
        JsonNode josnNode = JSON.getJosnNode(response.getContent());
        //Out.write(josnNode.toString(), new File("pixivApiTest.json"));
        JsonNode illusts = josnNode.get("illusts");
        for (JsonNode illust : illusts) {
            PixivItem item = PixivItem.builder()
                    .author(illust.get("user").get("name").textValue())
                    .favorite(illust.get("total_bookmarks").intValue())
                    .imageUrl(illust.get("image_urls").get("large").textValue())
                    .pictureId(illust.get("id").toString())
                    .title(illust.get("title").textValue())
                    .view(illust.get("total_view").intValue())
                    //.request(downloadIllust(illust.get("image_urls").get("large").textValue()))
                    .build();
            process(item);
        }
        return null;
    }
}
