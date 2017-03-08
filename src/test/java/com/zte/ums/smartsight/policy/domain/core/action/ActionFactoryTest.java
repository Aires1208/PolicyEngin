package com.zte.ums.smartsight.policy.domain.core.action;

import com.zte.ums.smartsight.policy.domain.core.PolicyException;
import com.zte.ums.smartsight.policy.domain.model.ActionTypeEnum;
import com.zte.ums.smartsight.policy.domain.model.EventType;
import com.zte.ums.smartsight.policy.domain.model.PolicyAction;
import com.zte.ums.smartsight.policy.domain.model.ResultEvent;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.matchers.JUnitMatchers.containsString;

/**
 * Created by root on 11/8/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ActionFactoryTest {
    ActionFactory actionFactory;
    //    private ActionFactory actionFactory;
    private PolicyAction mailAction;
    private PolicyAction smsAction;
    private PolicyAction uiAction;
    private PolicyAction nullAction;
    private ResultEvent resultEvent;

    @Before
    public void setUp() throws Exception {
        actionFactory = new ActionFactory();

        mailAction = new PolicyAction();
        List<String> receiver = newArrayList("zhang.pei162@zte.com.cn", "126941@zte.com.cn", "10183966@zte.com.cn");
        mailAction.setReceiverInfo(receiver);
        List<String> policyNames = newArrayList(EventType.APP_CALLHEAVY_CRITICAL.getErrorName());
        mailAction.setPolicyNames(policyNames);
        mailAction.setPolicyActionName("MailAction");
        mailAction.setActionTypeEnum(ActionTypeEnum.MAIL);


        smsAction = new PolicyAction();
        List<String> smsreceiver = newArrayList("12345678923");
        smsAction.setReceiverInfo(smsreceiver);
        List<String> smspolicyNames = newArrayList(EventType.APP_CALLHEAVY_CRITICAL.getErrorName());
        smsAction.setPolicyNames(smspolicyNames);
        smsAction.setPolicyActionName("SMSAction");
        smsAction.setActionTypeEnum(ActionTypeEnum.SMS);

        uiAction = new PolicyAction();
        List<String> uireceiver = newArrayList("test");
        uiAction.setReceiverInfo(uireceiver);
        List<String> uipolicyNames = newArrayList(EventType.APP_CALLHEAVY_CRITICAL.getErrorName());
        uiAction.setPolicyNames(uipolicyNames);
        uiAction.setPolicyActionName("SMSAction");
        uiAction.setActionTypeEnum(ActionTypeEnum.UI);

        nullAction = new PolicyAction();
        List<String> nullreceiver = newArrayList("test");
        nullAction.setReceiverInfo(nullreceiver);
        List<String> nullpolicyNames = newArrayList(EventType.APP_CALLHEAVY_CRITICAL.getErrorName());
        nullAction.setPolicyNames(nullpolicyNames);
        nullAction.setPolicyActionName("SMSAction");
        nullAction.setActionTypeEnum(ActionTypeEnum.UI);

        resultEvent = new ResultEvent();
        resultEvent.setEventType("10011");
        resultEvent.setStartTime(System.currentTimeMillis() - 60 * 1000);
        resultEvent.setEndTime(System.currentTimeMillis());
        resultEvent.setObjDN("app=ems");
        resultEvent.setDetail("CALLS(100)>10 in last 10 min");

    }

    @Test
    public void should_be_trigger_mailAction_test() throws Exception {
        actionFactory.triggerAction(newArrayList(mailAction), resultEvent);

    }

    @Test
    public void should_be_trigger_smsAction_test() throws Exception {
        actionFactory.triggerAction(newArrayList(smsAction), resultEvent);
    }

    @Test
    public void shoud_be_trigger_uiAction_test() throws Exception {
        actionFactory.triggerAction(newArrayList(uiAction), resultEvent);
    }

    @Test
    public void should_be_trigger_nullAction_test() throws Exception {
        NullableAction nullableAction = new NullableAction();
        try {
            nullableAction.execute(nullAction, resultEvent);
        } catch (PolicyException e) {
            Assert.assertThat(e.getMessage(), containsString("action does not exists,please check whether it is defined"));
        }

    }

}