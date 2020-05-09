package me.tianjx98.Jscrapy.dashboard.bean;

import me.tianjx98.Jscrapy.http.Request;

import java.util.List;

/**
 * @ClassName SchedulerInfo
 * @Description TODO
 * @Author tianjx98
 * @Date 2020-05-06 13:52
 */
public class SchedulerInfo {
    /**
     * 调度器当前请求数
     */
    private Integer currentRequestNum;
    /**
     * 添加到调度器的请求总数
     */
    private Integer totalRequestNum;
    /**
     * 被过滤器过滤掉的重复请求
     */
    private Integer repeatRequestNum;
    private List<Request> data;

    public Integer getTotalRequestNum() {
        return totalRequestNum;
    }

    public void setTotalRequestNum(Integer totalRequestNum) {
        this.totalRequestNum = totalRequestNum;
    }

    public Integer getCurrentRequestNum() {
        return currentRequestNum;
    }

    public void setCurrentRequestNum(Integer currentRequestNum) {
        this.currentRequestNum = currentRequestNum;
    }

    public Integer getRepeatRequestNum() {
        return repeatRequestNum;
    }

    public void setRepeatRequestNum(Integer repeatRequestNum) {
        this.repeatRequestNum = repeatRequestNum;
    }

    public List<Request> getData() {
        return data;
    }

    public void setData(List<Request> data) {
        this.data = data;
    }
}
