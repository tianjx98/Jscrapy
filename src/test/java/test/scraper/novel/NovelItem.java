package test.scraper.novel;

import lombok.Builder;
import lombok.Data;
import me.tianjx98.Jscrapy.pipeline.Item;

/**
 * @ClassName NovelItem
 * @Description TODO
 * @Author tianjx98
 * @Date 2019-08-18 16:02
 */
@Builder
@Data
public class NovelItem extends Item {
    Integer chapterNum;
    String novelName;
    String chapterName;
    String content;
}
