package com.zte.ums.smartsight.policy.domain.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by root on 11/14/16.
 */
public class ResultEventTest {
    @Test
    public void toStringTest() throws Exception {
        ResultEvent resultEvent = new ResultEvent();
        resultEvent.setEventType(EventType.APP_CALLHEAVY_CRITICAL.getErrorName());
        resultEvent.setStartTime(1479125209850l);
        resultEvent.setEndTime(1479125269850l);
        resultEvent.setObjDN("app=ems");
        resultEvent.setDetail("app=ems,call_heavy");

//        System.out.println(resultEvent.toString());
        Assert.assertEquals("ResultEvent{eventType=APP_CALLHEAVY_CRITICAL, startTime=1479125209850, endTime=1479125269850, objDN='app=ems', detail='app=ems,call_heavy'}", resultEvent.toString());
    }

}