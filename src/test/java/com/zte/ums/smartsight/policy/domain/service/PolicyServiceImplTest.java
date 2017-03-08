package com.zte.ums.smartsight.policy.domain.service;

import com.zte.ums.smartsight.policy.domain.core.PolicyException;
import com.zte.ums.smartsight.policy.domain.core.cache.PolicyCache;
import com.zte.ums.smartsight.policy.domain.model.*;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.mockito.Mockito.when;

/**
 * Created by root on 11/8/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PolicyServiceImplTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @InjectMocks
    PolicyService policyService = new PolicyServiceImpl();
    @Autowired
    private TestRestTemplate restTemplate;
    @Mock
    private PolicyCache cache;
    @Mock
    private PolicyRepo policyRepo;
    @Mock
    private PolicyActionRepo policyActionRepo;
    private Policy policy;

    @Before
    public void setup() throws Exception {
        MockitoAnnotations.initMocks(this);
        policy = new Policy();

        policy.setPolicyName("APP_CALL_HEAVY");
        policy.setPolicyActions(newArrayList("Mail", "SMS", "UI"));
        Condition condition = new Condition();
        condition.setObjDN("app=ems");
        condition.setEventTypes(newArrayList(EventType.APP_CALLHEAVY_CRITICAL.getErrorName(), EventType.APP_ERRORHEAVY_CRITICAL.getErrorName()));
        policy.setCondition(condition);
    }

    @Test
    public void should_be_return_success_when_policy_not_exist() throws Exception {


        Position position = new Position();
        position.setPolicyName("APP_CALL_HEAVY");
        position.setExist(true);

        when(cache.isPolicyExist(policy)).thenReturn(position);

        policyService.addPolicy(policy);
    }

    @Test
    public void should_be_throw_POLICY_ALREADY_EXISTS_exception() {
        Position position = new Position();
        when(cache.isPolicyExist(policy)).thenReturn(position);

        try {
            policyService.addPolicy(policy);
        } catch (PolicyException e) {
            Assert.assertThat(e.getMessage(), containsString(PolicyException.POLICY_ALREADY_EXISTS));

        }
    }

    @Test
    public void should_be_return_success_when_call_getPolicyByAppName_and_policy_exist() throws Exception {
        Map<String, Policy> policyMaps = new HashMap<String, Policy>();
        policyMaps.put(policy.getPolicyName(), policy);
        when(cache.getCache()).thenReturn(policyMaps);
        Assert.assertEquals(1, policyService.getPolicyByAppName("app=ems").getStatus());

    }

    @Test
    public void should_be_throw_policy_not_exception_when_call_getPolicyByAppName_and_policy_not_exist() throws Exception {
        Map<String, Policy> policyMaps = new HashMap<String, Policy>();
        policyMaps.put(policy.getPolicyName(), policy);
        when(cache.getCache()).thenThrow(new PolicyException(PolicyException.POLICY_NOT_EXISTS));
        Result result = policyService.getPolicyByAppName("app");
        System.out.println(result.toString());
        Assert.assertThat(result.toString(), containsString(PolicyException.POLICY_NOT_EXISTS));


    }

    @Test
    public void should_be_return_success_when_policies_exist_call_getPolicies() throws Exception {

        Map<String, Policy> policyMaps = new HashMap<String, Policy>();
        policyMaps.put(policy.getPolicyName(), policy);
        when(cache.getCache()).thenReturn(policyMaps);
        Assert.assertEquals(1, policyService.getPolicies().getStatus());
    }

    @Test
    public void should_be_throw_exception_when_policies_exist_not_exist() throws Exception {

        Map<String, Policy> policyMaps = new HashMap<String, Policy>();
        policyMaps.put(policy.getPolicyName(), policy);

        when(cache.getCache()).thenThrow(new PolicyException(PolicyException.POLICY_NOT_EXISTS));
        Result result = policyService.getPolicies();
        System.out.println(result.toString());
        Assert.assertThat(result.toString(), containsString(PolicyException.POLICY_NOT_EXISTS));

    }

    @Test
    public void deleteByPolicyName() throws Exception {
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return "add success";
            }
        }).when(policyRepo).deletePolicy(policy);

        Map<String, Policy> policyMaps = new HashMap<String, Policy>();
        policyMaps.put(policy.getPolicyName(), policy);
        when(cache.getCache()).thenReturn(policyMaps);

        Assert.assertEquals("SUCCESS", policyService.deleteByPolicyName("APP_CALL_HEAVY").getResMsg());

    }

    @Test
    public void should_be_throw_policy_exception_deleteByPolicyName() throws Exception {
        Mockito.doThrow(new PolicyException(PolicyException.POLICY_NOT_EXISTS)).when(policyRepo).deletePolicy(policy);

        Map<String, Policy> policyMaps = new HashMap<String, Policy>();
        policyMaps.put(policy.getPolicyName(), policy);
        when(cache.getCache()).thenReturn(policyMaps);

        Result result = policyService.deleteByPolicyName("APP_CAL");

        System.out.println(result.toString());

        Assert.assertThat(result.toString(), containsString(PolicyException.POLICY_NOT_EXISTS));

    }

    @Test
    public void updatePolicy() throws Exception {
        when(cache.isEventTypesExistByPolicyName(policy)).thenReturn(new Position());
        Assert.assertEquals(1, policyService.updatePolicy(policy).getStatus());
    }

    @Test
    public void getCommonInfo() throws Exception {
        PolicyAction policyAction = new PolicyAction();

        List<String> receiver = newArrayList("zhang.pei162@zte.com.cn");
        policyAction.setReceiverInfo(receiver);
        List<String> policyNames = newArrayList(EventType.APP_CALLHEAVY_CRITICAL.getErrorName());
        policyAction.setPolicyNames(policyNames);
        policyAction.setPolicyActionName("MailAction");
        policyAction.setActionTypeEnum(ActionTypeEnum.MAIL);

        when(policyActionRepo.getPolicyActions()).thenReturn(newArrayList(policyAction));
        Assert.assertEquals("{\"resInfo\":\"SUCCESS\",\"status\":1}", policyService.getCommonInfo().getCommonInfo().get("resMsg").toString());
    }

}