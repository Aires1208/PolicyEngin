package com.zte.ums.smartsight.policy.domain.service;

import com.zte.ums.smartsight.policy.domain.core.PolicyActionException;
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
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.junit.matchers.JUnitMatchers.containsString;
import static org.mockito.Mockito.*;

/**
 * Created by root on 11/8/16.
 */
//@RunWith(SpringRunner.class)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@WebAppConfiguration
public class PolicyActionServiceImplTest {
//    @Autowired
//    private TestRestTemplate restTemplate;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @InjectMocks
    PolicyActionService policyActionService = new PolicyActionServiceImpl();
    @Mock
    private PolicyActionRepo policyActionRepo;

    private PolicyAction policyAction;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        policyAction = new PolicyAction();

        List<String> receiver = newArrayList("zhang.pei162@zte.com.cn");
        policyAction.setReceiverInfo(receiver);
        List<String> policyNames = newArrayList(EventType.APP_CALLHEAVY_CRITICAL.getErrorName());
        policyAction.setPolicyNames(policyNames);
        policyAction.setPolicyActionName("MailAction");
        policyAction.setActionTypeEnum(ActionTypeEnum.MAIL);

    }

    @Test
    public void should_be_return_calls_number_when_mock_addPolicyAction() {

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return "add success";
            }
        }).when(policyActionRepo).putPolicyAction(policyAction);

        policyActionService.addPolicyAction(policyAction);

        verify(policyActionRepo, times(1)).putPolicyAction(policyAction);

    }

    @Test
    public void should_be_throw_policy_action_already_exist_exception() {
        when(policyActionRepo.isPolicyActionExist(policyActionRepo.getPolicyActions(), policyAction.getPolicyActionName())).thenReturn(true);

        try {
            policyActionService.addPolicyAction(policyAction);
        } catch (PolicyActionException e) {
            Assert.assertThat(e.getMessage(), containsString(PolicyActionException.POLICY_ACTION_ALREADY_EXISTS));
        }
    }

    @Test
    public void should_be_throw_policy_exception_when_policyaction_is_null() {
        Mockito.doThrow(new PolicyActionException(PolicyActionException.POLICY_ACTION_NOT_EXISTS)).when(policyActionRepo).putPolicyAction(policyAction);

        policyActionService.addPolicyAction(policyAction);

        verify(policyActionRepo, times(1)).putPolicyAction(policyAction);
    }

    @Test
    public void should_be_return_ok_when_call_deleteByPolicyActionName_and_policyaction_is_exist() throws Exception {

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return "add success";
            }
        }).when(policyActionRepo).deleteByPolicyActionName("MailAction");


        when(policyActionRepo.isPolicyActionExist(policyActionRepo.getPolicyActions(), "MailAction")).thenReturn(true);

        Result resultBuilder = policyActionService.deleteByPolicyActionName("MailAction");
        System.out.println(resultBuilder.toString());

        verify(policyActionRepo, times(1)).deleteByPolicyActionName("MailAction");
    }

    @Test
    public void should_be_throw_policy_action_not_exit_exception() {
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return "add success";
            }
        }).when(policyActionRepo).deleteByPolicyActionName("Mail");

        when(policyActionRepo.isPolicyActionExist(policyActionRepo.getPolicyActions(), "Mail")).thenReturn(false);

        try {
            Result result = policyActionService.deleteByPolicyActionName("Mail");
            System.out.println(result.toString());
        } catch (PolicyActionException e) {
            Assert.assertThat(e.getMessage(), containsString(PolicyActionException.POLICY_ACTION_NOT_EXISTS));
        }
    }

    @Test
    public void should_be_return_ok_when_call_updatePolicyAction_and_policyaction_is_exist() {
        when(policyActionRepo.isPolicyActionExist(policyActionRepo.getPolicyActions(), policyAction.getPolicyActionName())).thenReturn(true);

        try {
            policyActionService.updatePolicyAction(policyAction);
        } catch (PolicyActionException e) {
            Assert.assertThat(e.getMessage(), containsString(PolicyActionException.POLICY_ACTION_NOT_EXISTS));
        }

    }

    @Test
    public void should_be_throw_policy_action_not_exist_exception_when_call_updatePolicyAction() {
        when(policyActionRepo.isPolicyActionExist(policyActionRepo.getPolicyActions(), policyAction.getPolicyActionName())).thenReturn(false);
        try {
            policyActionService.updatePolicyAction(policyAction);
        } catch (PolicyActionException e) {
            Assert.assertThat(e.getMessage(), containsString(PolicyActionException.POLICY_ACTION_NOT_EXISTS));
        }

    }

    @Test
    public void should_be_return_policyactions_and_message_is_ok_when_call_getPolicyActions() {
        when(policyActionRepo.getPolicyActions()).thenReturn(newArrayList(policyAction));

        Assert.assertEquals(1, policyActionService.getPolicyActions().getStatus());
    }

    @Test
    public void should_be_throw_policyaction_exception_when_call_getPolicyActions() {
        when(policyActionRepo.getPolicyActions()).thenThrow(new PolicyActionException(PolicyActionException.POLICY_ACTION_NOT_EXISTS));

        Result result = policyActionService.getPolicyActions();
        Assert.assertThat(result.toString(), containsString(PolicyActionException.POLICY_ACTION_NOT_EXISTS));
    }

}