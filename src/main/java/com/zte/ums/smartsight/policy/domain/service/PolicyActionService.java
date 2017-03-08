package com.zte.ums.smartsight.policy.domain.service;

import com.zte.ums.smartsight.policy.domain.model.PolicyAction;
import com.zte.ums.smartsight.policy.domain.model.Result;

/**
 * Created by 10165228 on 2016/10/27.
 */
public interface PolicyActionService {

    Result addPolicyAction(PolicyAction policyAction);

    Result deleteByPolicyActionName(String policyActionName);

    Result updatePolicyAction(PolicyAction policyAction);

    Result getPolicyActions();

}
