package com.zte.ums.smartsight.policy.domain.core.action;

import com.zte.ums.smartsight.policy.domain.model.PolicyAction;
import com.zte.ums.smartsight.policy.domain.model.ResultEvent;

/**
 * Created by 10172605 on 2016/9/26.
 */
public class SMSAction implements BaseAction {
    @Override
    public void execute(PolicyAction policyAction, ResultEvent resultEvent) {
        System.out.println("send sms success...");
    }
}