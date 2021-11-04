package me.tianjx98.jscrapy.core.impl2;


import java.io.IOException;
import java.util.List;

import org.jetbrains.annotations.NotNull;

import lombok.extern.log4j.Log4j2;
import me.tianjx98.jscrapy.config.JscrapyConfig;
import me.tianjx98.jscrapy.core.*;
import me.tianjx98.jscrapy.http.Request;
import me.tianjx98.jscrapy.http.impl.DefaultResponse;
import me.tianjx98.jscrapy.pipeline.Item;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;
import reactor.core.publisher.Flux;

/**
 * @author tianjx98
 * @date 2021/11/1 13:05
 */
@Log4j2
public class DefaultSpiderEngine extends AbstractEngine {

    public DefaultSpiderEngine(JscrapyConfig config, List<Spider> spiders) {
        super(config, spiders);
    }

    public DefaultSpiderEngine(JscrapyConfig config, String classPackage)
                    throws InstantiationException, IllegalAccessException {
        super(config, classPackage);
    }

    @Override
    public Engine start() {
        Flux.fromIterable(spiders).map(Spider::startRequests).subscribe(scheduler::addRequests);
        state = EngineState.RUN;
        nextRequest();
        return this;
    }

    @Override
    protected void nextRequest() {
        // 如果没有请求了, 关闭引擎
        if (scheduler.isEmpty() && downloader.isEmpty()) {
            stop();
            return;
        }
        if (state == EngineState.RUN && !scheduler.isEmpty() && !downloader.needBlock(scheduler.peekRequest())) {
            final Request request = scheduler.pollRequest();
            if (request != null) {
                downloader.download(request, wrapCallback(request));
                nextRequest();
            }
        }
    }

    /**
     * 使用匿名类实现, 与new一个常规类没有任何性能差异
     * 
     * @param request 请求对象
     * @return 包装后的回调对象, 每当请求完成或失败后会调用对应的回调方法
     */
    private Callback wrapCallback(Request request) {
        return new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                downloader.remove(request);
                log.error("请求失败: {}", request, e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                log.info("请求成功: {}", request);
                downloader.remove(request);
                final Flux<? extends Element> elements =
                                request.getCallback().apply(new DefaultResponse(request, response));
                elements.subscribe(this::subscribeElement);
                nextRequest();
            }

            private void subscribeElement(Element element) {
                if (element instanceof Request) {
                    scheduler.addRequest((Request) element);
                }
                if (pipelineManager != null && element instanceof Item) {
                    pipelineManager.processItem((Item) element, request.getSpider());
                }
            }
        };
    }

}
