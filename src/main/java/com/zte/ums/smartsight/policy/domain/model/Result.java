package com.zte.ums.smartsight.policy.domain.model;


import com.alibaba.fastjson.JSONObject;

/**
 * Created by aires on 2016/10/8.
 */
public class Result {
    public static final int SUCCESS = 1;
    public static final int FAIL = 0;
    private int status = SUCCESS;
    private JSONObject data;
    private String resMsg = "";

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    @Override
    public String toString() {
        return "Result{" +
                "status=" + status +
                ", data=" + data +
                ", resMsg='" + resMsg + '\'' +
                '}';
    }
}
