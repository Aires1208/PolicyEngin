package com.zte.ums.smartsight.policy.controller;

import com.zte.ums.smartsight.policy.Application;
import com.zte.ums.smartsight.policy.domain.model.*;
import com.zte.ums.smartsight.policy.domain.service.PolicyActionService;
import com.zte.ums.smartsight.policy.domain.service.PolicyService;
import com.zte.ums.smartsight.policy.domain.utils.JsonUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by root on 11/8/16.
 */
//@RunWith(SpringRunner.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class PolicyControllerTest {
    @InjectMocks
    PolicyController policyController;
    @Autowired
    private WebApplicationContext context;
    @Mock
    private PolicyService policyService;
    @Mock
    private PolicyActionService policyActionService;
    private MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(policyController).build();
    }

    @Test
    public void should_be_return_excepted_result_when_call_restful_api() throws Exception {
        ResultCommonInfo resultCommonInfo = new ResultCommonInfo();

        CommonInfo commonInfo = new CommonInfo();
        ResMsg resMsg = new ResMsg();
        resMsg.setResInfo("Test resmsg !");
        resMsg.setStatus(0);
        commonInfo.setResMsg(resMsg);
        commonInfo.setPolicyActions(newArrayList("Mail action", "UI action"));
        commonInfo.setEventTypes(newArrayList(EventType.APP_CALLHEAVY_CRITICAL));
//        /policy/commonInfo
        resultCommonInfo.setCommonInfo(JsonUtils.obj2JSON(commonInfo));

        when(policyService.getCommonInfo()).
                thenReturn(resultCommonInfo);

        mockMvc.perform(get("/policy/commonInfo")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("true").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"commonInfo\":{\"policyActions\":[\"Mail action\",\"UI action\"],\"eventTypes\":[\"APP_CALLHEAVY_CRITICAL\"],\"resMsg\":{\"resInfo\":\"Test resmsg !\",\"status\":0}}}"));

//        policyController.queryCommonInfo();
    }

    @Test
    public void should_be_return_success_addPolicy() throws Exception {
        Policy policy = new Policy();
        policy.setPolicyName("APP_CALL_HEAVY");
        policy.setPolicyActions(newArrayList("Mail", "SMS", "UI"));
        Condition condition = new Condition();
        condition.setObjDN("app=ems");
        condition.setEventTypes(newArrayList(EventType.APP_CALLHEAVY_CRITICAL.getErrorName(), EventType.APP_ERRORHEAVY_CRITICAL.getErrorName()));
        policy.setCondition(condition);

        when(policyService.addPolicy(policy)).thenReturn(ResultBuilder.newResult().status(1).data(JsonUtils.obj2JSON(policy)).message("SUCCESS").build());

        mockMvc.perform(post("/policy")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"condition\":{\"objDN\":\"app=ems\",\"eventTypes\":[\"calls heavy\",\"errors heavy\"]},\"policyName\":\"APP_CALL_HEAVY\",\"policyActions\":[\"Mail\",\"SMS\",\"UI\"]}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(content().string("Result{status=0, data={\"condition\":{\"objDN\":\"app=ems\",\"eventTypes\":[\"calls heavy\",\"errors heavy\"]},\"policyName\":\"APP_CALL_HEAVY\",\"policyActions\":[\"Mail\",\"SMS\",\"UI\"]}, resMsg='OK'}"));
        Result result = policyController.addPolicy(policy);

        Assert.assertEquals("SUCCESS", result.getResMsg());

    }

    @Test
    public void should_be_return_success_when_policy_is_exist_call_deletePolicy() throws Exception {
        when(policyService.deleteByPolicyName("APP_CALL_HEAVY")).thenReturn(ResultBuilder.newResult().message("SUCCESS").status(1).build());
        mockMvc.perform(delete("/policy?policyName=APP_CALL_HEAVY")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"status\":1,\"data\":null,\"resMsg\":\"SUCCESS\"}"));


//        policyController.deletePolicy("APP_CALL_HEAVY");
    }

    @Test
    public void should_be_return_success_update_policy_when_policy_exist() throws Exception {
        Policy policy = new Policy();
        policy.setPolicyName("APP_CALL_HEAVY");
        policy.setPolicyActions(newArrayList("Mail", "SMS"));
        Condition condition = new Condition();
        condition.setObjDN("app=ems");
        condition.setEventTypes(newArrayList(EventType.APP_CALLHEAVY_CRITICAL.getErrorName(), EventType.APP_ERRORHEAVY_CRITICAL.getErrorName()));
        policy.setCondition(condition);

        when(policyService.updatePolicy(policy)).thenReturn(ResultBuilder.newResult().status(1).data(JsonUtils.obj2JSON(policy)).message("SUCCESS").build());

        mockMvc.perform(put("/policy")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"condition\":{\"objDN\":\"app=ems\",\"eventTypes\":[\"calls heavy\",\"errors heavy\"]},\"policyName\":\"APP_CALL_HEAVY\",\"policyActions\":[\"Mail\",\"SMS\",\"UI\"]}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(content().string("Result{status=0, data={\"condition\":{\"objDN\":\"app=ems\",\"eventTypes\":[\"calls heavy\",\"errors heavy\"]},\"policyName\":\"APP_CALL_HEAVY\",\"policyActions\":[\"Mail\",\"SMS\",\"UI\"]}, resMsg='OK'}"));
        Result result = policyController.updatePolicy(policy);

        Assert.assertEquals("SUCCESS", result.getResMsg());
//        policyController.updatePolicy(policy);
    }

    @Test
    public void should_be_return_all_policy_when_call_queryPolicy() throws Exception {
        Policy policy = new Policy();
        policy.setPolicyName("APP_CALL_HEAVY");
        policy.setPolicyActions(newArrayList("Mail", "SMS"));
        Condition condition = new Condition();
        condition.setObjDN("app=ems");
        condition.setEventTypes(newArrayList(EventType.APP_CALLHEAVY_CRITICAL.getErrorName(), EventType.APP_ERRORHEAVY_CRITICAL.getErrorName()));
        policy.setCondition(condition);
        Policies policies = new Policies();
        policies.setPolicies(newArrayList(policy));
        when(policyService.getPolicies()).thenReturn(ResultBuilder.newResult().status(1).message("SUCCESS").data(JsonUtils.obj2JSON(policies)).build());

        mockMvc.perform(get("/policy")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("true").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"status\":1,\"data\":{\"policies\":[{\"condition\":{\"objDN\":\"app=ems\",\"eventTypes\":[\"APP_CALLHEAVY_CRITICAL\",\"APP_ERRORHEAVY_CRITICAL\"]},\"policyName\":\"APP_CALL_HEAVY\",\"policyActions\":[\"Mail\",\"SMS\"]}]},\"resMsg\":\"SUCCESS\"}"));
//        policyController.queryPolicy();
    }

    @Test
    public void should_be_return_policyaction_list_after_addPolicyAction_success() throws Exception {
        PolicyAction policyAction = new PolicyAction();
        List<String> receiver = new ArrayList<String>();
        receiver.add("zhang.pei162@zte.com.cn");
        policyAction.setReceiverInfo(receiver);
        List<String> policyNames = new ArrayList<String>();
        policyNames.add("mail");
        policyAction.setPolicyNames(policyNames);
        policyAction.setPolicyActionName("MailAction");
        policyAction.setActionTypeEnum(ActionTypeEnum.MAIL);
        System.out.println(JsonUtils.obj2JSON(policyAction).toString());

        when(policyActionService.addPolicyAction(policyAction)).thenReturn(ResultBuilder.newResult().status(1).data(JsonUtils.obj2JSON(policyAction)).message("SUCCESS").build());

        mockMvc.perform(post("/policy/policyAction")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"actionTypeEnum\":\"MAIL\",\"receiverInfo\":[\"zhang.pei162@zte.com.cn\"],\"policyNames\":[\"mail\"],\"policyActionName\":\"MailAction\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
//                .andExpect(content().string("Result{status=0, data={\"condition\":{\"objDN\":\"app=ems\",\"eventTypes\":[\"calls heavy\",\"errors heavy\"]},\"policyName\":\"APP_CALL_HEAVY\",\"policyActions\":[\"Mail\",\"SMS\",\"UI\"]}, resMsg='OK'}"));
        Result result = policyController.addPolicyAction(policyAction);
        Assert.assertEquals("SUCCESS", result.getResMsg());

//        policyController.addPolicyAction(policyAction);
    }

    @Test
    public void should_be_return_success_when_policyaction_exist_deleteByPolicyActionName() throws Exception {

        when(policyActionService.deleteByPolicyActionName("MailAction")).thenReturn(ResultBuilder.newResult().message("SUCCESS").status(1).build());
        mockMvc.perform(delete("/policy/policyAction?policyActionName=MailAction")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"status\":1,\"data\":null,\"resMsg\":\"SUCCESS\"}"));
        policyController.deleteByPolicyActionName("MailAction");
    }

    @Test
    public void should_update_PolicyAction_when_exist() throws Exception {
        PolicyAction policyAction = new PolicyAction();
        List<String> receiver = new ArrayList<String>();
        receiver.add("zhang.pei162@zte.com.cn");
        policyAction.setReceiverInfo(receiver);
        List<String> policyNames = new ArrayList<String>();
        policyNames.add("mail");
        policyAction.setPolicyNames(policyNames);
        policyAction.setPolicyActionName("MailAction");
        policyAction.setActionTypeEnum(ActionTypeEnum.MAIL);

        when(policyActionService.updatePolicyAction(policyAction)).thenReturn(ResultBuilder.newResult().status(1).data(JsonUtils.obj2JSON(policyAction)).message("SUCCESS").build());

        mockMvc.perform(put("/policy/policyAction")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("{\"actionTypeEnum\":\"MAIL\",\"receiverInfo\":[\"zhang.pei162@zte.com.cn\"],\"policyNames\":[\"mail\"],\"policyActionName\":\"MailAction\"}")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
        Result result = policyController.updatePolicyAction(policyAction);
        Assert.assertEquals("SUCCESS", result.getResMsg());


//        policyController.updatePolicyAction(policyAction);
    }

    @Test
    public void should_be_return_all_policyaction_when_call_getPolicyActions() throws Exception {
        PolicyAction policyAction = new PolicyAction();
        List<String> receiver = new ArrayList<String>();
        receiver.add("zhang.pei162@zte.com.cn");
        policyAction.setReceiverInfo(receiver);
        List<String> policyNames = new ArrayList<String>();
        policyNames.add("mail");
        policyAction.setPolicyNames(policyNames);
        policyAction.setPolicyActionName("MailAction");
        policyAction.setActionTypeEnum(ActionTypeEnum.MAIL);
        PolicyActions policyActions = new PolicyActions();
        policyActions.setPolicyActions(newArrayList(policyAction));

        when(policyActionService.getPolicyActions()).thenReturn(ResultBuilder.newResult().status(1).message("SUCCESS").data(JsonUtils.obj2JSON(policyActions)).build());

        mockMvc.perform(get("/policy/policyAction")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content("true").accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"status\":1,\"data\":{\"policyActions\":[{\"actionTypeEnum\":\"MAIL\",\"receiverInfo\":[\"zhang.pei162@zte.com.cn\"],\"policyNames\":[\"mail\"],\"policyActionName\":\"MailAction\"}]},\"resMsg\":\"SUCCESS\"}"));
//

//        policyController.getPolicyActions();
    }

}