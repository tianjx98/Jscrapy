package me.tianjx98.Jscrapy.http.client;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.concurrent.FutureCallback;

public interface HttpClient {
    void execute(HttpRequestBase request, FutureCallback<HttpResponse> callback);

    void close();
}
