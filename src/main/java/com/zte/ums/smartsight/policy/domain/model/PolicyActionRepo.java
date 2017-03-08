package com.zte.ums.smartsight.policy.domain.model;

import java.util.List;

/**
 * Created by 10165228 on 2016/10/27.
 */
public interface PolicyActionRepo {

    void putPolicyAction(PolicyAction policyAction);

    void deleteByPolicyActionName(String policyActionName);

    List<PolicyAction> getPolicyActions();

    PolicyAction getPolicyActionByPolicyName(String policyActionName);

    boolean isPolicyActionExist(List<PolicyAction> _policyActions, String policyActionName);

}
