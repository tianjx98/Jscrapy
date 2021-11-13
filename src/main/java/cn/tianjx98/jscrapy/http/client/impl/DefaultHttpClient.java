package cn.tianjx98.jscrapy.http.client.impl;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import cn.tianjx98.jscrapy.http.Request;
import cn.tianjx98.jscrapy.http.client.HttpClient;
import lombok.extern.log4j.Log4j2;
import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;

/**
 * @author tianjx98
 * @date 2021/11/2 12:47
 */
@Log4j2
public class DefaultHttpClient implements HttpClient {
    OkHttpClient client = new OkHttpClient.Builder().readTimeout(15, TimeUnit.SECONDS)
            .build();

    @Override
    public void execute(Request request, Callback callback) {
        Call call = null;
        try {
            call = client.newCall(request.buildRequest());
            callback.onResponse(call, call.execute());
        } catch (IOException e) {
            log.error(e);
            callback.onFailure(call, e);
        }
    }

    @Override
    public void executeAsync(Request request, Callback callback) {
        client.newCall(request.buildRequest()).enqueue(callback);
    }

    @Override
    public void close() {
        try {
            client.dispatcher().executorService().shutdown();
            client.connectionPool().evictAll();
            final Cache cache = client.cache();
            if (cache != null) {
                cache.close();
            }
        } catch (IOException e) {
            log.error("请求客户端关闭失败", e);
        }
    }
}
