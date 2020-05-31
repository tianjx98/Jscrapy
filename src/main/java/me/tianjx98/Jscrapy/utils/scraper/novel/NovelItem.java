package me.tianjx98.Jscrapy.utils.scraper.novel;

import lombok.Builder;
import lombok.Data;
import me.tianjx98.Jscrapy.pipeline.Item;


/**
 * @ClassName NovelItem
 * @Description TODO
 * @Author tianjx98
 * @Date 2019-08-18 16:02
 */
@Data
@Builder
public class NovelItem extends Item {
    Integer chapterNum;
    String novelName;
    String chapterName;
    String content;
}

