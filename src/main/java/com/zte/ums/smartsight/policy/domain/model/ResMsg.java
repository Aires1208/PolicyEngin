package com.zte.ums.smartsight.policy.domain.model;

/**
 * Created by root on 10/12/16.
 */
public class ResMsg {
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    private int status = SUCCESS;
    private String resInfo = "";

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getResInfo() {
        return resInfo;
    }

    public void setResInfo(String resInfo) {
        this.resInfo = resInfo;
    }
}
