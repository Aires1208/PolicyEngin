package com.zte.ums.smartsight.policy.domain.core.action;

import com.zte.ums.smartsight.policy.domain.core.PolicyException;
import com.zte.ums.smartsight.policy.domain.model.PolicyAction;
import com.zte.ums.smartsight.policy.domain.model.ResultEvent;

/**
 * Created by aires on 2016/9/26.
 */
public class NullableAction implements BaseAction {
    @Override
    public void execute(PolicyAction policyAction, ResultEvent resultEvent) {
        throw new PolicyException(PolicyException.ACTION_NOT_EXISTS);
    }
}
