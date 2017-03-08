package com.zte.ums.smartsight.policy.domain.model;

import org.apache.hadoop.hbase.shaded.org.junit.Assert;
import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by root on 11/14/16.
 */
public class ConditionTest {
    @Test
    public void should_be_return_condition_string_toStringTest() throws Exception {
        Condition condition = new Condition();
        condition.setObjDN("app=ems");
        condition.setEventTypes(newArrayList(EventType.APP_CALLHEAVY_CRITICAL.getErrorName(), EventType.APP_ERRORHEAVY_CRITICAL.getErrorName()));
        Assert.assertEquals("Condition{objDN='app=ems', eventTypes=[APP_CALLHEAVY_CRITICAL, APP_ERRORHEAVY_CRITICAL]}", condition.toString());

    }

}