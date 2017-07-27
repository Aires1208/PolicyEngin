package com.zte.ums.smartsight.policy.domain.core.action;

import com.zte.ums.smartsight.policy.domain.model.PolicyAction;
import com.zte.ums.smartsight.policy.domain.model.ResultEvent;

/**
 * Created by aires on 2016/9/26.
 */
public class DirectUIAction implements BaseAction {
    @Override
    public void execute(PolicyAction policyAction, ResultEvent resultEvent) {
        System.out.println("direct ui success...");
    }
}
