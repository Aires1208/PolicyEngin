package com.zte.ums.smartsight.policy.domain.model;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by root on 10/12/16.
 */
public class ResultCommonInfo {

    private JSONObject commonInfo;

    public JSONObject getCommonInfo() {
        return commonInfo;
    }

    public void setCommonInfo(JSONObject commonInfo) {
        this.commonInfo = commonInfo;
    }

    @Override
    public String toString() {
        return "ResultCommonInfo{" +
                "commonInfo=" + commonInfo +
                '}';
    }
}
