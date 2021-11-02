package test;

import com.fasterxml.jackson.databind.JsonNode;
import me.tianjx98.jscrapy.http.impl.Request;
import me.tianjx98.jscrapy.http.impl.Response;
import me.tianjx98.jscrapy.utils.JSON;
import me.tianjx98.jscrapy.utils.PixivApiSpider;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.LinkedList;
import java.util.List;

/**
 * @ClassName PixivApiSpiderTest
 * @Description TODO
 * @Author tianjx98
 * @Date 2019-08-19 22:31
 */
public class PixivApiSpiderTest extends PixivApiSpider {

    public static void main(String[] args) {
        start(PixivApiSpiderTest.class);
    }

    @Override
    public List<Request> parse(Response response) {
        LinkedList<Request> requests = new LinkedList<>();
        // 获取图片详细信息
        requests.add(getIllustDetail("55841546", this::saveResult));
        // 获取排行榜
        requests.add(getIllustRanking(null, null, this::saveResult));
        // 获取某个图片的相关图片
        requests.add(getIllustRelated("55841546", this::saveResult));
        // 获取用户收藏的图片
        requests.add(getUserBookmarkIllusts("23513709", this::saveResult));
        // 获取用户详细信息
        requests.add(getUserDetail("23513709", this::saveResult));
        // 获取用户的作品
        requests.add(getUserIllusts("2188232", this::saveResult));
        return requests;
    }

    public List<Request> saveResult(Response response) {
        Charset charset = Charset.defaultCharset();
        try (FileOutputStream outputStream = new FileOutputStream(new File("pixivApiTest.json"), true)) {
            JsonNode josnNode = JSON.getJosnNode(response.getContent());
            outputStream.write(josnNode.toString().getBytes(charset));
            outputStream.write(",".getBytes(charset));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
