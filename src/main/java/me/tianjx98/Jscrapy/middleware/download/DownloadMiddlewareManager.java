package me.tianjx98.Jscrapy.middleware.download;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * @ClassName DownloadMiddlewareManager
 * @Description TODO
 * @Author tianjx98
 * @Date 2019-08-17 15:41
 */
public class DownloadMiddlewareManager {
    private static final Logger LOGGER = LoggerFactory.getLogger(DownloadMiddlewareManager.class);
    private List<DownloadMiddleware> downloadMiddlewares = new LinkedList<>();

    public DownloadMiddlewareManager(TreeMap<Integer, DownloadMiddleware> downloadMwTreeMap) {
        for (Map.Entry<Integer, DownloadMiddleware> entry : downloadMwTreeMap.entrySet()) {
            downloadMiddlewares.add(entry.getValue());
        }
        LOGGER.info("DownloadMiddlewares = " + downloadMiddlewares);
    }

    public void openDownloadMiddlewares() {

    }
}
