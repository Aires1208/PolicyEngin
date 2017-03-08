package com.zte.ums.smartsight.policy.domain.model;

import org.junit.Assert;
import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by root on 11/14/16.
 */
public class PolicyTest {
    @Test
    public void should_be_return_policy_string_when_call_toString() throws Exception {

        Policy policy = new Policy();
        policy.setPolicyName("APP_CALL_HEAVY");
        policy.setPolicyActions(newArrayList("Mail", "SMS", "UI"));
        Condition condition = new Condition();
        condition.setObjDN("app=ems");
        condition.setEventTypes(newArrayList(EventType.APP_CALLHEAVY_CRITICAL.getErrorName(), EventType.APP_ERRORHEAVY_CRITICAL.getErrorName()));
        policy.setCondition(condition);

        System.out.println(policy.toString());
        Assert.assertEquals("Policy{policyName='APP_CALL_HEAVY', condition=Condition{objDN='app=ems', eventTypes=[APP_CALLHEAVY_CRITICAL, APP_ERRORHEAVY_CRITICAL]}, policyActionsNames=[Mail, SMS, UI]}", policy.toString());

    }

}