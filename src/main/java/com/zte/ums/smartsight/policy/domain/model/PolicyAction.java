package com.zte.ums.smartsight.policy.domain.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 10165228 on 2016/10/27.
 */
public class PolicyAction implements Serializable {

    private static final long serialVersionUID = 1L;
    private String policyActionName;
    private ActionTypeEnum actionTypeEnum;

    private List<String> policyNames;

    private List<String> receiverInfo;


    public List<String> getReceiverInfo() {
        return receiverInfo;
    }

    public void setReceiverInfo(List<String> receiverInfo) {
        this.receiverInfo = receiverInfo;
    }

    public String getPolicyActionName() {
        return policyActionName;
    }

    public void setPolicyActionName(String policyActionName) {
        this.policyActionName = policyActionName;
    }

    public ActionTypeEnum getActionTypeEnum() {
        return actionTypeEnum;
    }

    public void setActionTypeEnum(ActionTypeEnum actionTypeEnum) {
        this.actionTypeEnum = actionTypeEnum;
    }

    public List<String> getPolicyNames() {
        return policyNames;
    }

    public void setPolicyNames(List<String> policyNames) {
        this.policyNames = policyNames;
    }

    @Override
    public String toString() {
        return "PolicyAction{" +
                "policyActionName='" + policyActionName + '\'' +
                ", actionTypeEnum=" + actionTypeEnum +
                ", policyNames=" + policyNames +
                ", receiverInfo=" + receiverInfo +
                '}';
    }
}
