package test.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import lombok.extern.log4j.Log4j2;
import me.tianjx98.jscrapy.core.AbstractSpider;
import me.tianjx98.jscrapy.core.Element;
import me.tianjx98.jscrapy.core.annotation.ScraperElement;
import me.tianjx98.jscrapy.http.Request;
import me.tianjx98.jscrapy.http.Response;
import me.tianjx98.jscrapy.http.impl.DefaultRequest;
import reactor.core.publisher.Flux;

/**
 * @author tianjx98
 * @date 2021/11/3 13:04
 */
@ScraperElement
@Log4j2
public class TestSpider extends AbstractSpider {
    @Override
    public String getName() {
        return "testSpider";
    }

    @Override
    public Flux<String> startUrls() {
        return Flux.range(1, 10).map(i -> String.format("http://dev.hzero.org:8080/hutl/v1/test/public/%s", i));
        // return Flux.range(1, 10).map(i -> String.format("http://localhost:8080/%s", i));
    }

    @Override
    public Flux<Request> startRequests() {
        final String url = "http://hscs-gatewayuat.wanyol.com/hspm/v1/tax/taxInquire?developerCode=556666929";
        final String[] types = {"HEYTAP", "JINRITEMAI", "TMALL", "JD"};
        final String body =
                        "{\"unkwnClaimFlag\":\"Y\",\"platform\":\"TMALL\",\"__dirty\":true,\"__id\":1006,\"_status\":\"create\",\"gatewayBillIdList\":[]}";
        final List<Request> requests = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            // final String trueBody = String.format(body, types[i % 4]);
            final DefaultRequest request = new DefaultRequest(this, url, this::parse);
            request.setRequestBuilder((builder, request1) -> {
                builder.url(url);
                // builder.header("Authorization", "6f452c35-73dd-430b-9d12-ae9b9a488e95");
                // builder.post(RequestBody.create(body, MediaType.parse("application/json; charset=utf-8")));
                return builder.get();
            });
            requests.add(request);
        }
        // System.out.println(requests);
        // return Flux.empty();
        return Flux.fromIterable(requests);
    }

    private AtomicInteger i = new AtomicInteger(0);

    @Override
    public Flux<Element> parse(Response response) {
        final String body = response.getBody();
        log.info(body);

        if (!body.startsWith("{\"code\":\"200\"")) {
            log.error(body);
        }
        return Flux.empty();
    }

    public static void main(String[] args) {

    }


}
