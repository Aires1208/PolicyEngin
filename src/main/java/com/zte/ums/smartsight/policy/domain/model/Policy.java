package com.zte.ums.smartsight.policy.domain.model;

import java.util.List;

/**
 * Created by aires on 2016/9/26.
 */

public class Policy {
    private String policyName;
    private Condition condition;
    private List<String> policyActionsNames;

    public List<String> getPolicyActions() {
        return policyActionsNames;
    }

    public void setPolicyActions(List<String> policyActions) {

        this.policyActionsNames = policyActions;

    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    public Condition getCondition() {
        return condition;
    }

    public void setCondition(Condition condition) {
        this.condition = condition;
    }

    @Override
    public String toString() {
        return "Policy{" +
                "policyName='" + policyName + '\'' +
                ", condition=" + condition +
                ", policyActionsNames=" + policyActionsNames +
                '}';
    }
}
