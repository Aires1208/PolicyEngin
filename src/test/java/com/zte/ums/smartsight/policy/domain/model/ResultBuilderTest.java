package com.zte.ums.smartsight.policy.domain.model;

import com.zte.ums.smartsight.policy.domain.utils.JsonUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by root on 11/9/16.
 */
public class ResultBuilderTest {
    private Policy policy = new Policy();

    @Before
    public void setUp() throws Exception {
        policy.setPolicyName("APP_CALL_HEAVY");
        policy.setPolicyActions(newArrayList("Mail", "SMS", "UI"));
        Condition condition = new Condition();
        condition.setObjDN("app=ems");
        condition.setEventTypes(newArrayList(EventType.APP_CALLHEAVY_CRITICAL.getErrorName(), EventType.APP_ERRORHEAVY_CRITICAL.getErrorName()));
        policy.setCondition(condition);
    }

    @Test
    public void should_be_return_result_when_call_newResult() throws Exception {
        Assert.assertEquals("OK", ResultBuilder.newResult().status(1).data(JsonUtils.obj2JSON(policy)).message("OK").build().getResMsg());

    }

    @Test
    public void newResult1() throws Exception {
        Assert.assertEquals("OK", ResultBuilder.newResult(1, JsonUtils.obj2JSON(policy), "OK").build().getResMsg());

    }

    @Test
    public void status() throws Exception {
        Assert.assertEquals(1, ResultBuilder.newResult().status(1).build().getStatus());
    }

    @Test
    public void data() throws Exception {
        Assert.assertEquals("OK", ResultBuilder.newResult().data(JsonUtils.obj2JSON(policy)).message("OK").build().getResMsg());
    }

    @Test
    public void message() throws Exception {
        Assert.assertEquals("OK", ResultBuilder.newResult().data(JsonUtils.obj2JSON(policy)).message("OK").build().getResMsg());

    }


}