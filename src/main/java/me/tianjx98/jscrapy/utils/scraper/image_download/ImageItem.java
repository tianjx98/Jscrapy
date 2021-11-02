package me.tianjx98.jscrapy.utils.scraper.image_download;

import lombok.Builder;
import lombok.Data;
import me.tianjx98.jscrapy.pipeline.Item;

/**
 * @ClassName ImageItem
 * @Description TODO
 * @Author tianjx98
 * @Date 2020-05-28 13:49
 */
@Data
@Builder
public class ImageItem extends Item {
    String name;
    String url;
}
