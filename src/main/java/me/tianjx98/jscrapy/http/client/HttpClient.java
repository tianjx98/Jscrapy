package me.tianjx98.jscrapy.http.client;

import me.tianjx98.jscrapy.http.Request;
import okhttp3.Callback;

public interface HttpClient {

    void execute(Request request, Callback callback);

    void executeAsync(Request request, Callback callback);

    void close();
}
