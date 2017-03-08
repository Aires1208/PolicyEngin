package com.zte.ums.smartsight.policy.domain.model;

import com.alibaba.fastjson.JSONObject;

import static com.zte.ums.smartsight.policy.domain.model.Result.SUCCESS;

/**
 * Created by 10172605 on 2016/10/10.
 */
public class ResultBuilder {
    private int status = SUCCESS;
    private JSONObject data;
    private String message;

    protected ResultBuilder() {
    }

    public static ResultBuilder newResult() {
        return new ResultBuilder();
    }

    public static ResultBuilder newResult(int status, JSONObject data, String message) {
        ResultBuilder resultBuilder = new ResultBuilder();
        resultBuilder.status(status);
        resultBuilder.message(message);
        resultBuilder.data(data);
        return resultBuilder;
    }

    public ResultBuilder status(int status) {
        this.status = status;
        return this;
    }

    public ResultBuilder data(JSONObject data) {
        this.data = data;
        return this;
    }

    public ResultBuilder message(String message) {
        this.message = message;
        return this;
    }

    public Result build() {
        Result result = new Result();
        result.setResMsg(message);
        result.setData(data);
        result.setStatus(status);
        return result;
    }

}
