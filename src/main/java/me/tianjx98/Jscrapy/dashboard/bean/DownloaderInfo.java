package me.tianjx98.Jscrapy.dashboard.bean;

import me.tianjx98.Jscrapy.http.Request;

import java.util.Map;
import java.util.Set;

/**
 * @ClassName DownloaderInfo
 * @Description TODO
 * @Author tianjx98
 * @Date 2020-05-06 15:03
 */
public class DownloaderInfo {
    /**
     * 已下载请求总数
     */
    private Integer downloadingRequests;
    /**
     * 当前正在下载的请求数
     */
    private Integer totalDownloadRequests;
    private Map<String, Set<Request>> data;

    public Integer getDownloadingRequests() {
        return downloadingRequests;
    }

    public void setDownloadingRequests(Integer downloadingRequests) {
        this.downloadingRequests = downloadingRequests;
    }

    public Integer getTotalDownloadRequests() {
        return totalDownloadRequests;
    }

    public void setTotalDownloadRequests(Integer totalDownloadRequests) {
        this.totalDownloadRequests = totalDownloadRequests;
    }

    public Map<String, Set<Request>> getData() {
        return data;
    }

    public void setData(Map<String, Set<Request>> data) {
        this.data = data;
    }
}
