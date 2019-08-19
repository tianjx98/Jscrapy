package test;

import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.Response;
import me.tianjx98.Jscrapy.utils.PixivApiSpider;

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
        requests.add(getIllustDetail("55841546", this::saveResult));
        requests.add(getIllustRanking(null, null, this::saveResult));
        requests.add(getIllustRelated("55841546", this::saveResult));
        requests.add(getUserBookmarkIllusts("23513709", this::saveResult));
        requests.add(getUserDetail("23513709", this::saveResult));
        requests.add(getUserIllusts("2188232", this::saveResult));
        return requests;
    }

    public List<Request> saveResult(Response response) {
        Charset charset = Charset.defaultCharset();
        try (FileOutputStream outputStream = new FileOutputStream(new File("pixivApiTest.json"), true)) {
            outputStream.write(response.getContent().getBytes(charset));
            outputStream.write(",".getBytes(charset));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
