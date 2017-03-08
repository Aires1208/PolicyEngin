package com.zte.ums.smartsight.policy.domain.core;

import com.zte.ums.smartsight.policy.domain.core.cache.PolicyCache;
import com.zte.ums.smartsight.policy.domain.model.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.mockito.Mockito.*;

/**
 * Created by root on 11/10/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PolicyManagerCenterTest {
    @Mock
    PolicyCache cache;

    @Mock
    PolicyActionRepo policyActionRepo;
    @InjectMocks

    PolicyManagerCenter policyManagerCenter = new PolicyManagerCenter();
    Map<String, Policy> policyCachedMap = new HashMap<String, Policy>();
    private ResultEvent resultEvent;
    private PolicyAction mailAction;
    private Policy policy;

    @Before
    public void setUp() throws Exception {
        resultEvent = new ResultEvent();
        resultEvent.setEventType("10011");
        resultEvent.setStartTime(System.currentTimeMillis() - 60 * 1000);
        resultEvent.setEndTime(System.currentTimeMillis());
        resultEvent.setObjDN("app=ems");
        resultEvent.setDetail("app=ems,call_heavy");

        mailAction = new PolicyAction();

        List<String> receiver = newArrayList("zhang.pei162@zte.com.cn", "10129605@zte.com.cn");
        mailAction.setReceiverInfo(receiver);
        mailAction.setPolicyNames(newArrayList("MailActionTest"));
        mailAction.setPolicyActionName("MailActionTest");
        mailAction.setActionTypeEnum(ActionTypeEnum.MAIL);


        policy = new Policy();
        policy.setPolicyName("MailActionTest");
        policy.setPolicyActions(newArrayList("MailActionTest"));
        Condition condition = new Condition();
        condition.setObjDN("app= IaasOps, service= IaasOps_fm");
        condition.setEventTypes(newArrayList(EventType.APP_CALLHEAVY_CRITICAL.getErrorName()));
        policy.setCondition(condition);


        policyCachedMap.put(policy.getPolicyName(), policy);
    }

    @Test
    public void trigger() throws PolicyException {

        when(cache.getCache()).thenReturn(policyCachedMap);

        when(policyActionRepo.getPolicyActionByPolicyName(mailAction.getPolicyActionName())).thenReturn(mailAction);

        policyManagerCenter.trigger(resultEvent);

        verify(cache, times(1)).getCache();

    }

    @Test
    public void should_be_throw_exception_trigger() throws PolicyException {

        when(cache.getCache()).thenReturn(new HashMap<String, Policy>());

        policyManagerCenter.trigger(resultEvent);

        verify(cache, times(1)).getCache();

    }

    @Test
    public void should_be_all_solution_trigger() throws PolicyException {
        ResultEvent event = new ResultEvent();
        event.setEventType("10012");
        event.setStartTime(System.currentTimeMillis() - 60 * 1000);
        event.setEndTime(System.currentTimeMillis());
        event.setObjDN("app= IaasOps, service= IaasOps_fm");
        event.setDetail("app= IaasOps, service= IaasOps_fm");
        when(cache.getCache()).thenReturn(policyCachedMap);

        when(policyActionRepo.getPolicyActionByPolicyName(mailAction.getPolicyActionName())).thenReturn(mailAction);

        policyManagerCenter.trigger(event);

        verify(cache, times(1)).getCache();

    }

    @Test
    public void should_be_throw_policy_exception() {
        when(cache.getCache()).thenThrow(new PolicyException(PolicyException.POLICY_NOT_EXISTS));
        policyManagerCenter.trigger(resultEvent);

    }

}