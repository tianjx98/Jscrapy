package me.tianjx98.Jscrapy.http.client.impl;

import me.tianjx98.Jscrapy.http.Request;
import me.tianjx98.Jscrapy.http.client.HttpClient;
import org.apache.http.HttpResponse;
import org.apache.http.concurrent.FutureCallback;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @ClassName Selenium
 * @Description TODO
 * @Author tianjx98
 * @Date 2019-08-16 19:13
 */
public class SeleniumClient implements HttpClient {

    private ChromeDriver driver = new ChromeDriver();

    @Override
    public void execute(Request request, FutureCallback<HttpResponse> callback) {
        driver.get(request.getUrl().toString());

    }

    @Override
    public void close() {

    }
}
