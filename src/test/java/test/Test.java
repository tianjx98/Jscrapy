package test;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicInteger;

import org.jetbrains.annotations.NotNull;

import lombok.extern.log4j.Log4j2;
import okhttp3.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.GroupedFlux;
import test.engine.TestItem;
import test.engine.TestPipeline;
import test.engine.TestSpider;


@Log4j2
public class Test {
    OkHttpClient client = new OkHttpClient();
    private AtomicInteger a = new AtomicInteger(0);
    private AtomicInteger b = new AtomicInteger(0);
    private AtomicInteger c = new AtomicInteger(0);


    public static void main(String[] args)
                    throws ExecutionException, InterruptedException, TimeoutException, IOException {
        //final Flux< ? extends Number> flux = test2();
        ////flux.groupBy(Number::getClass).;
        //
        //final Flux<GroupedFlux<Character, String>> groupedFluxFlux = Flux.just("a1", "b1", "c1", "a2", "b2", "c2")
        //        .groupBy(s -> s.charAt(0));
        //final Flux<String> stringFlux = groupedFluxFlux.concatMap(groupedFlux -> groupedFlux
        //        .startWith("Group " + groupedFlux.key()));
        //
        //groupedFluxFlux.subscribe(System.out::println);
        final TestItem testItem = new TestItem("1");
        final TestPipeline pipeline = new TestPipeline();
        final Type genericInterface = pipeline.getClass().getGenericInterfaces()[0];
        System.out.println(genericInterface);
    }

    private static Flux<? extends Number> test2() {
        final Flux<Integer> f1 = Flux.just(1);
        final Flux<Long> f2 = Flux.just(2L);
        return Flux.concat(f1, f2);
    }

    private void test() throws IOException {
        int n = 1000000000;
        long start = System.currentTimeMillis();
        testB(n);
        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
        testC(n);
        System.out.println(System.currentTimeMillis() - start);
        start = System.currentTimeMillis();
        testA(n);
        System.out.println(System.currentTimeMillis() - start);
    }

    private void testA(int n) throws IOException {
        for (int i = 0; i < n; i++) {
            wrapCallback(a).onResponse(null, null);
        }
    }

    private void testB(int n) throws IOException {
        for (int i = 0; i < n; i++) {
            b.getAndIncrement();
        }
    }

    private void testC(int n) throws IOException {
        for (int i = 0; i < n; i++) {
            new TestC().onResponse(null, null);
        }
    }

    class TestC implements Callback {
        @Override
        public void onFailure(Call call, IOException e) {

        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {
            c.getAndIncrement();
        }
    }


    private Callback wrapCallback(AtomicInteger i) {
        return new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {}

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                i.getAndIncrement();
            }
        };
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
