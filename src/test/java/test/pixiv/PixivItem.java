package test.pixiv;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;
import me.tianjx98.Jscrapy.pipeline.Item;

/**
 * @ClassName PixivItem
 * @Description TODO
 * @Author tian
 * @Date 2019/7/23 14:48
 * @Version 1.0
 */
@Data
@Builder
public class PixivItem extends Item {
    @JsonProperty("图片页面URL")
    String pageUrl;
    @JsonProperty("图片编号")
    String pictureId;
    @JsonProperty("图片标题")
    String title;
    @JsonProperty("作者")
    String author;
    @JsonProperty("图片URL")
    String imageUrl;
    @JsonProperty("点赞数")
    int upvote;
    @JsonProperty("收藏数")
    int favorite;
    @JsonProperty("浏览数")
    int view;
    @JsonProperty("评论数")
    int comment;
}
