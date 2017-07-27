package com.zte.ums.smartsight.policy.domain.model;

import java.util.List;

/**
 * Created by aires on 2016/9/26.
 */
public class Condition {
    private String objDN;
    private List<String> eventTypes;

    public String getObjDN() {
        return objDN;
    }

    public void setObjDN(String objDN) {
        this.objDN = objDN;
    }

    public List<String> getEventTypes() {
        return eventTypes;
    }

    public void setEventTypes(List<String> eventTypes) {
        this.eventTypes = eventTypes;
    }

    @Override
    public String toString() {
        return "Condition{" +
                "objDN='" + objDN + '\'' +
                ", eventTypes=" + eventTypes +
                '}';
    }
}
