package com.zte.ums.smartsight.policy.domain.model;

/**
 * Created by 10172605 on 2016/9/26.
 */
public enum ActionTypeEnum {
    MAIL("send mail"),
    UI("direct UI"),
    SMS("send sms");

    private String key;

    ActionTypeEnum(String key) {
        this.key = key;
    }
}
