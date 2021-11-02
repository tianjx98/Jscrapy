package me.tianjx98.jscrapy.core.impl2;


import lombok.extern.log4j.Log4j2;
import me.tianjx98.jscrapy.config.JscrapyConfig;
import me.tianjx98.jscrapy.core.AbstractEngine;
import me.tianjx98.jscrapy.core.Engine;
import me.tianjx98.jscrapy.core.EngineState;
import me.tianjx98.jscrapy.core.Spider;
import me.tianjx98.jscrapy.http.Request;
import me.tianjx98.jscrapy.http.impl.DefaultResponse;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.stream.Collectors;

/**
 * @author tianjx98
 * @date 2021/11/1 13:05
 */
@Log4j2
public class DefaultSpiderEngine extends AbstractEngine {

    public DefaultSpiderEngine(JscrapyConfig config) {
        super(config);
    }

    @Override
    public Engine start() {
        Flux.fromIterable(spiders).map(Spider::startRequests).subscribe(scheduler::addRequests);
        state = EngineState.RUN;
        nextRequest();
        return this;
    }

    protected void nextRequest() {
        // 如果没有请求了, 关闭引擎
        if (scheduler.isEmpty() && downloader.isEmpty()) {
            stop();
        }
        if (state == EngineState.RUN && !scheduler.isEmpty() && !downloader.needBlock()) {
            final Request request = scheduler.nextRequest();
            downloader.download(request, wrapCallback(request));
        }
    }

    private Callback wrapCallback(Request request) {
        return new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                downloader.remove(request);
                log.error("请求失败: {}", request, e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                log.debug("请求成功: {}", request);
                downloader.remove(request);
                final Flux<Request> nextRequests = request.getCallback().apply(new DefaultResponse(request, response));
                scheduler.addRequests(nextRequests);
                nextRequest();
            }
        };
    }

}
