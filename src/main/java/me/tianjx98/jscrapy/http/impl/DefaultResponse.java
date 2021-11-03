package me.tianjx98.jscrapy.http.impl;

import java.io.IOException;
import java.util.Objects;

import lombok.extern.log4j.Log4j2;
import me.tianjx98.jscrapy.http.Request;
import me.tianjx98.jscrapy.http.Response;

@Log4j2
public class DefaultResponse implements Response {
    private Request request;
    private okhttp3.Response response;

    public DefaultResponse(Request request, okhttp3.Response response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public int statusCode() {
        return response.code();
    }

    @Override
    public String getBody() {
        try {
            return Objects.requireNonNull(response.body()).string();
        } catch (IOException e) {
            log.error("读取响应报文失败", e);
            return "";
        }
    }

    @Override
    public Request getRequest() {
        return request;
    }
}
