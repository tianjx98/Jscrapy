package me.tianjx98.jscrapy.http.client.impl;

import java.io.IOException;

import me.tianjx98.jscrapy.http.Request;
import me.tianjx98.jscrapy.http.client.HttpClient;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Response;

/**
 * @author tianjx98
 * @date 2021/11/2 12:47
 */
public class DefaultHttpClient implements HttpClient {
    OkHttpClient client = new OkHttpClient();

    @Override
    public void execute(me.tianjx98.jscrapy.http.Request request, Callback callback) {
        try {
            Thread.sleep(1000);
            callback.onResponse(null, new Response.Builder().addHeader("key", "val").build());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void executeAsync(Request request, Callback callback) {
        final Call call = client.newCall(new okhttp3.Request.Builder().url(request.getUrl()).get().build());
        call.enqueue(callback);
    }

    @Override
    public void close() {}
}
