package me.tianjx98.Jscrapy.dashboard.controller;

import me.tianjx98.Jscrapy.dashboard.bean.DownloaderInfo;
import me.tianjx98.Jscrapy.dashboard.bean.EngineInfo;
import me.tianjx98.Jscrapy.dashboard.bean.SchedulerInfo;
import me.tianjx98.Jscrapy.dashboard.service.IScrapeEngineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @ClassName ScrapeEngineController
 * @Description TODO
 * @Author tianjx98
 * @Date 2020-05-05 11:22
 */
@RestController
@RequestMapping("/engine")
public class ScrapeEngineController {

    @Autowired
    IScrapeEngineService engineService;

    /**
     * 控制引擎
     *
     * @param command
     */
    @GetMapping("/control/{command}")
    public void control(@PathVariable("command") String command) {
        switch (command) {
            case "start"://启动引擎, 如果引擎已经启动会自动重启
                engineService.start();
                break;
            case "pause"://暂停
                engineService.pause();
                break;
            case "close"://关闭引擎
                engineService.close();
                break;
        }
    }

    /**
     * 获取调度器中请求对象
     *
     * @param page     页数
     * @param pageSize 每页容量
     * @return
     */
    @GetMapping("/scheduler/{page}/{pageSize}")
    public SchedulerInfo scheduler(@PathVariable("page") int page, @PathVariable("pageSize") int pageSize) {
        return engineService.scheduler(page, pageSize);
    }

    @GetMapping("/downloader")
    public DownloaderInfo downloader() {
        return engineService.downloading();
    }

    @GetMapping("/info")
    public EngineInfo engineInfo() {
        return engineService.info();
    }
}
