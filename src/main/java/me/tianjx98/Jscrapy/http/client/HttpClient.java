package me.tianjx98.Jscrapy.http.client;

import me.tianjx98.Jscrapy.http.Request;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;

public interface HttpClient {
    void execute(Request request, FutureCallback<HttpResponse> callback);

    void close();
}
