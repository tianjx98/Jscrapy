package me.tianjx98.Jscrapy.dashboard.service.impl;

import me.tianjx98.Jscrapy.core.Downloader;
import me.tianjx98.Jscrapy.core.Engine;
import me.tianjx98.Jscrapy.core.Scheduler;
import me.tianjx98.Jscrapy.dashboard.bean.DownloaderInfo;
import me.tianjx98.Jscrapy.dashboard.bean.EngineInfo;
import me.tianjx98.Jscrapy.dashboard.bean.SchedulerInfo;
import me.tianjx98.Jscrapy.dashboard.service.IScrapeEngineService;
import me.tianjx98.Jscrapy.http.Request;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.ListIterator;

/**
 * @ClassName IScrapeEngineServiceImpl
 * @Description TODO
 * @Author tianjx98
 * @Date 2020-05-05 11:13
 */
@Service
public class IScrapeEngineServiceImpl implements IScrapeEngineService {

    private Engine engine;

    @Override
    public void start() {
        // 如果已经启动,则关闭后重启
        if (engine != null) {
            engine.close();
        }
        engine = new Engine();
        engine.start();
    }

    @Override
    public void pause() {
        engine.pause();
    }

    @Override
    public EngineInfo info() {
        return new EngineInfo(engine == null || engine.isClosed(), engine == null || engine.isPaused());
    }

    @Override
    public void close() {
        engine.close();
    }

    @Override
    public SchedulerInfo scheduler(int page, int pageSize) {
        int index = (page - 1) * pageSize;
        LinkedList<Request> requests = new LinkedList<>();
        Scheduler scheduler = engine.getScheduler();
        SchedulerInfo schedulerInfo = new SchedulerInfo();
        schedulerInfo.setTotalRequestNum(scheduler.getTotalRequestNum());
        schedulerInfo.setCurrentRequestNum(scheduler.size());
        schedulerInfo.setRepeatRequestNum(scheduler.getRepeatRequestNum());
        if (index >= scheduler.size()) return schedulerInfo;

        ListIterator<Request> iterator = scheduler.getQueue().listIterator(index);
        int count = 0;
        while (iterator.hasNext() && count++ < pageSize) {
            requests.add(iterator.next());
        }
        schedulerInfo.setData(requests);
        return schedulerInfo;
    }

    @Override
    public DownloaderInfo downloading() {
        DownloaderInfo downloaderInfo = new DownloaderInfo();
        Downloader downloader = engine.getDownloader();
        downloaderInfo.setTotalDownloadRequests(downloader.getCount());
        downloaderInfo.setDownloadingRequests(downloader.getSize());
        downloaderInfo.setData(downloader.getCrawling());
        return downloaderInfo;
    }
}
