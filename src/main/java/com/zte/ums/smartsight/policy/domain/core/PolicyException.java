package com.zte.ums.smartsight.policy.domain.core;

/**
 * Created by aires on 2016/9/26.
 */
public class PolicyException extends RuntimeException {
    public static final String ACTION_NOT_EXISTS = "action does not exists,please check whether it is defined";
    public static final String POLICY_NOT_EXISTS = "policy does not exists, please check whether it is defined";
    public static final String POLICY_ALREADY_EXISTS = "policy already exist in hbase";
    public static final String ADD_POLICY_ERROR = "add policy error";
    public static final String DELETE_POLICY_ERROR = "delete policy error";
    public static final String MODIFY_POLICY_ERROR = "modify policy error";
    public static final String QUERY_POLICY_ERROR = "query policy error";
    public static final String OBJ_TO_BYTES_ERROR = "object write to bytes error";
    public static final String BYTES_AS_OBJ_ERROR = "bytes read as object error";

    public PolicyException(String message) {
        super(message);
    }
}
