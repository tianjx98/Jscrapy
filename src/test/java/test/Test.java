package test;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;


public class Test {
    OkHttpClient client = new OkHttpClient();
    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException, IOException {
        final String r = new Test().run("https://www.baidu.com/");
        System.out.println(r);
    }

    String run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        try (Response response = client.newCall(request).execute()) {
            return response.body().string();
        }
    }
}
