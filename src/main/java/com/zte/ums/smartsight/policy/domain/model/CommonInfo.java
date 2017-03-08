package com.zte.ums.smartsight.policy.domain.model;

import java.util.List;

/**
 * Created by root on 10/12/16.
 */
public class CommonInfo {
    private ResMsg resMsg;
    private List<String> policyActionsNames;
    private List<EventType> eventTypes;

    public List<String> getPolicyActions() {
        return policyActionsNames;
    }

    public void setPolicyActions(List<String> policyActions) {
        this.policyActionsNames = policyActions;
    }

    public List<EventType> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(List<EventType> eventTypes) {
        this.eventTypes = eventTypes;
    }

    public ResMsg getResMsg() {
        return resMsg;
    }

    public void setResMsg(ResMsg resMsg) {
        this.resMsg = resMsg;
    }
}
