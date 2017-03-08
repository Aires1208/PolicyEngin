package com.zte.ums.smartsight.policy.domain.model;

/**
 * Created by 10172605 on 2016/9/26.
 */
public class PositionAction {
    private boolean isExist = false;
    private String policyActionName;

    public PositionAction() {
    }

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }

    public String getPolicyActionName() {
        return policyActionName;
    }

    public void setPolicyActionName(String policyActionName) {
        this.policyActionName = policyActionName;
    }
}
