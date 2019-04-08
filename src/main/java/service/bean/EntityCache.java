package service.bean;

/**
 * Created by jiaxiong on 2019-03-18 19:33
 */
public class EntityCache {
    /**
     * 保存的数据
     */
    private Object data;

    /**
     * 过期时间, 0表示永不失效
     */
    private long timeOut;

    /**
     * 最后更新时间
     */
    private long lastRefeshTime;

    public EntityCache(Object data, long timeOut, long lastRefeshTime) {
        this.data = data;
        this.timeOut = timeOut;
        this.lastRefeshTime = lastRefeshTime;
    }

    public Object getdata() {
        return data;
    }

    public void setdata(Object data) {
        this.data = data;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public long getLastRefeshTime() {
        return lastRefeshTime;
    }

    public void setLastRefeshTime(long lastRefeshTime) {
        this.lastRefeshTime = lastRefeshTime;
    }
}
