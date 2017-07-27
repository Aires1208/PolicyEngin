package com.zte.ums.smartsight.policy.domain.model;

/**
 * Created by aires on 2016/9/26.
 */
public class Position {
    private boolean isExist = false;
    private String policyName;

    public Position() {
    }

    public Position(boolean isExist) {
        this.isExist = isExist;
    }

    public boolean isExist() {
        return isExist;
    }

    public void setExist(boolean exist) {
        isExist = exist;
    }

    public String getPolicyName() {
        return policyName;
    }

    public void setPolicyName(String policyName) {
        this.policyName = policyName;
    }

    @Override
    public String toString() {
        return "Position{" +
                "isExist=" + isExist +
                ", policyName='" + policyName + '\'' +
                '}';
    }
}
