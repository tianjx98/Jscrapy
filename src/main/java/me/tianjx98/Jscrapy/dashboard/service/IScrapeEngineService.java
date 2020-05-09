package me.tianjx98.Jscrapy.dashboard.service;

import me.tianjx98.Jscrapy.dashboard.bean.DownloaderInfo;
import me.tianjx98.Jscrapy.dashboard.bean.EngineInfo;
import me.tianjx98.Jscrapy.dashboard.bean.SchedulerInfo;

public interface IScrapeEngineService {

    /**
     * 启动爬虫引擎
     */
    void start();

    /**
     * 暂停,或取消暂停
     */
    void pause();

    EngineInfo info();

    /**
     * 关闭引擎
     */
    void close();

    SchedulerInfo scheduler(int page, int pageSize);

    DownloaderInfo downloading();
}
