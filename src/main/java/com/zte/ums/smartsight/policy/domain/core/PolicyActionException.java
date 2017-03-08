package com.zte.ums.smartsight.policy.domain.core;

/**
 * Created by 10172605 on 2016/9/26.
 */
public class PolicyActionException extends RuntimeException {
    public static final String POLICY_ACTION_ALREADY_EXISTS = "policy action already exist in hbase";
    public static final String POLICY_ACTION_NOT_EXISTS = "policy action does not exists, please check whether it is defined";

    public PolicyActionException(String message) {
        super(message);
    }

}
