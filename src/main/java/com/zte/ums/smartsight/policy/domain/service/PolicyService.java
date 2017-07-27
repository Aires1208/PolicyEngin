package com.zte.ums.smartsight.policy.domain.service;

import com.zte.ums.smartsight.policy.domain.model.Policy;
import com.zte.ums.smartsight.policy.domain.model.Result;
import com.zte.ums.smartsight.policy.domain.model.ResultCommonInfo;

/**
 * Created by aires on 2016/9/26.
 */
public interface PolicyService {
    Result addPolicy(Policy policy);

    Result deleteByPolicyName(String policyName);

    Result updatePolicy(Policy policy);

    Result getPolicyByAppName(String appName);

    Result getPolicies();

    ResultCommonInfo getCommonInfo();


}
