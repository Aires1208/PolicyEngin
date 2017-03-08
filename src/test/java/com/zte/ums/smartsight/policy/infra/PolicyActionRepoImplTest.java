package com.zte.ums.smartsight.policy.infra;

import com.zte.ums.smartsight.policy.domain.constant.PolicyConstant;
import com.zte.ums.smartsight.policy.domain.model.ActionTypeEnum;
import com.zte.ums.smartsight.policy.domain.model.EventType;
import com.zte.ums.smartsight.policy.domain.model.PolicyAction;
import com.zte.ums.smartsight.policy.domain.model.PolicyActionRepo;
import com.zte.ums.smartsight.policy.domain.utils.JsonUtils;
import org.apache.hadoop.hbase.client.Scan;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.ResultsExtractor;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.mockito.Mockito.*;

/**
 * Created by root on 11/9/16.
 */
//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PolicyActionRepoImplTest {
    @InjectMocks
    PolicyActionRepo policyActionRepo = new PolicyActionRepoImpl();
    @Mock
    private HbaseTemplate hbaseTemplate;
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
    public void putPolicyAction() throws Exception {

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return "add success";
            }
        }).when(hbaseTemplate).put(PolicyConstant.POLICY_ACTION, policyAction.getPolicyActionName(), PolicyConstant.POLICY_ACTUON_CF, policyAction.getPolicyActionName(), JsonUtils.serialize(policyAction).getBytes());

        policyActionRepo.putPolicyAction(policyAction);

        verify(hbaseTemplate, times(1)).put(PolicyConstant.POLICY_ACTION, policyAction.getPolicyActionName(), PolicyConstant.POLICY_ACTUON_CF, policyAction.getPolicyActionName(), JsonUtils.serialize(policyAction).getBytes());

    }

    @Test
    public void deleteByPolicyActionName() throws Exception {
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return "delete success";
            }
        }).when(hbaseTemplate).delete(PolicyConstant.POLICY_ACTION, "MailAction", PolicyConstant.POLICY_ACTUON_CF, "MailAction");

        policyActionRepo.deleteByPolicyActionName("MailAction");
        verify(hbaseTemplate, times(1)).delete(PolicyConstant.POLICY_ACTION, "MailAction", PolicyConstant.POLICY_ACTUON_CF, "MailAction");
    }

    @Test
    public void should_be_return_policyaction_list_when_call_getPolicyActions() throws Exception {
        PolicyActionRepoImpl policyActionRepo = mock(PolicyActionRepoImpl.class);
        policyActionRepo.getPolicyActions().add(policyAction);
        Assert.assertEquals(0, policyActionRepo.getPolicyActions().size());
    }

    @Test
    public void should_be_return_policyaction_when_policyaction_is_exist_getPolicyActionByPolicyName() throws Exception {

        List<PolicyAction> policyActions = newArrayList(policyAction);
        when(hbaseTemplate.find(Matchers.eq(PolicyConstant.POLICY_ACTION), Matchers.any(Scan.class), Matchers.any(ResultsExtractor.class))).thenReturn(policyActions);
        List<PolicyAction> policyActionList = policyActionRepo.getPolicyActions();
        Assert.assertEquals(1, policyActionList.size());
    }

    @Test
    public void should_be_return_false_when_call_isPolicyActionExist_and_policyaction_not_exist() throws Exception {

//        PolicyAction> policyAction = newArrayList(policyAction);
        when(hbaseTemplate.find(Matchers.eq(PolicyConstant.POLICY_ACTION), Matchers.any(Scan.class), Matchers.any(ResultsExtractor.class))).thenReturn(policyAction);
        PolicyAction test = policyActionRepo.getPolicyActionByPolicyName("MailAction");
        Assert.assertEquals(ActionTypeEnum.MAIL, test.getActionTypeEnum());
    }

    @Test
    public void should_be_return_true_when_call_isPolicyActionExist_and_policyaction_is_exist() throws Exception {
//
//        when(policyActionRepo.isPolicyActionExist("MailAction")).thenReturn(true);
//        Assert.assertTrue(policyActionRepo.isPolicyActionExist("MailAction"));
    }

    @Test
    public void should_be_return_true_isPolicyActionExistTest() {
        Assert.assertTrue(policyActionRepo.isPolicyActionExist(newArrayList(policyAction), "MailAction"));
    }

    @Test
    public void should_be_return_false_isPolicyActionExistTest() {
        Assert.assertFalse(policyActionRepo.isPolicyActionExist(newArrayList(policyAction), "Action"));
    }

}