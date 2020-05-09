package me.tianjx98.Jscrapy.dashboard.bean;

/**
 * @ClassName EngineInfo
 * @Description TODO
 * @Author tianjx98
 * @Date 2020-05-07 11:52
 */
public class EngineInfo {
    /**
     * 标志引擎是否关闭
     */
    private Boolean closed;
    /**
     * 标志引擎是否暂停
     */
    private Boolean paused;

    public EngineInfo(Boolean closed, Boolean paused) {
        this.closed = closed;
        this.paused = paused;
    }

    public Boolean getClosed() {
        return closed;
    }

    public void setClosed(Boolean closed) {
        this.closed = closed;
    }

    public Boolean getPaused() {
        return paused;
    }

    public void setPaused(Boolean paused) {
        this.paused = paused;
    }
}
