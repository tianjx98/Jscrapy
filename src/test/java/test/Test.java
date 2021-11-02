package test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import com.google.common.collect.Lists;
import me.tianjx98.jscrapy.core.impl2.DefaultDownloader;
import org.jetbrains.annotations.NotNull;

import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import org.openqa.selenium.NotFoundException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


@Log4j2
public class Test {
    OkHttpClient client = new OkHttpClient();

    public static void main(String[] args)
                    throws ExecutionException, InterruptedException, TimeoutException, IOException {
        //testItf();
        //final DefaultDownloader downloader = new DefaultDownloader();

        final Optional<Flux<String>> stringFlux = Optional.of("2").map(Flux::just);

        Flux.just(Lists.newArrayList("a")).map(n -> {
            try {
                final Optional<Integer> integer = Optional.of(Integer.valueOf(n.get(0)));
                return integer;
            } catch (NumberFormatException e) {
                return Optional.empty();
            }
        }).filter(Optional::isPresent).map(Optional::get);

    }

    private static void testItf() throws IOException {
        final Test test = new Test();
        final String json = "{\n" + "    \"sourceSystem\": \"OPEN\",\n" + "    \"batchNum\": \"%s\",\n"
                        + "    \"interfaceCode\": \"INT_GEN_OPEN_CUST_REC_SYNCHRON\",\n" + "    \"lineCount\": 2,\n"
                        + "    \"commercianUnit\": \"CONTENT\",\n" + "    \"context\": [\n" + "        {\n"
                        + "            \"settlementNumber\": \"APST2021102000000017\",\n"
                        + "            \"updateTaxRate\": 0.06,\n" + "            \"businessUnit\": \"CONTENT\",\n"
                        + "            \"taxPayerType\": 0\n" + "        }\n" + "    ]\n" + "}";
        for (String batchNum : getBatchNums()) {
            test.post("http://dev.hzero.org:8080/hsdi-28090/v1/pub/out?organizationId=0",
                            String.format(json, batchNum));
        }
    }

    private static ArrayList<String> getBatchNums() {
        int start = 320;
        final ArrayList<String> list = new ArrayList<>(20);
        for (int i = 0; i < 50; i++) {
            list.add("TEST" + (start + i));
        }
        return list;
    }

    String post(String url, String body) throws IOException {
        final RequestBody requestBody = RequestBody.create(body, MediaType.get("application/json; charset=utf-8"));
        Request request = new Request.Builder().url(url).post(requestBody).build();
        final Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                log.error(e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                log.info(response.body().string());
            }
        });
        return "";
    }
}
