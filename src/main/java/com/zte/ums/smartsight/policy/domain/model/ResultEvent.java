package com.zte.ums.smartsight.policy.domain.model;

/**
 * Created by 10172605 on 2016/9/26.
 */
public class ResultEvent {
    private String eventType;
    private long startTime;
    private long endTime;
    private String objDN;
    private String detail;

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getObjDN() {
        return objDN;
    }

    public void setObjDN(String objDN) {
        this.objDN = objDN;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    @Override
    public String toString() {
        return "ResultEvent{" +
                "eventType=" + eventType +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", objDN='" + objDN + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}
