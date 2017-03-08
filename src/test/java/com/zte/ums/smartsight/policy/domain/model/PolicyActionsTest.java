package com.zte.ums.smartsight.policy.domain.model;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 11/8/16.
 */
public class PolicyActionsTest {
    private PolicyActions policyActions;

    @Before
    public void setup() {
        List<PolicyAction> policyActionList = new ArrayList();

        PolicyAction policyAction = new PolicyAction();
        List<String> receiver = new ArrayList<String>();
        receiver.add("zhang.pei162@zte.com.cn");
        policyAction.setReceiverInfo(receiver);
        List<String> policyNames = new ArrayList<String>();
        policyNames.add("mail");
        policyAction.setPolicyNames(policyNames);
        policyAction.setPolicyActionName("MailAction");
        policyAction.setActionTypeEnum(ActionTypeEnum.MAIL);

        PolicyAction policyAction1 = new PolicyAction();
        List<String> receiver1 = new ArrayList<String>();
        receiver.add("zhang.pei162@zte.com.cn");
        policyAction.setReceiverInfo(receiver1);
        List<String> policyNames1 = new ArrayList<String>();
        policyNames.add("SMS");
        policyAction.setPolicyNames(policyNames1);
        policyAction.setPolicyActionName("SMSAction");
        policyAction.setActionTypeEnum(ActionTypeEnum.SMS);

        PolicyAction policyAction2 = new PolicyAction();
        List<String> receiver2 = new ArrayList<String>();
        receiver.add("zhang.pei162@zte.com.cn");
        policyAction.setReceiverInfo(receiver2);
        List<String> policyNames2 = new ArrayList<String>();
        policyNames.add("UI");
        policyAction.setPolicyNames(policyNames2);
        policyAction.setPolicyActionName("UIAction");
        policyAction.setActionTypeEnum(ActionTypeEnum.UI);

        policyActionList.add(policyAction);
        policyActionList.add(policyAction1);
        policyActionList.add(policyAction2);

        policyActions = new PolicyActions();
        policyActions.setPolicyActions(policyActionList);

    }

    @Test
    public void should_be_return_all_policyaction_when_call_getPolicyActions() throws Exception {
        List<PolicyAction> policyActionTest = policyActions.getPolicyActions();
        System.out.println(policyActionTest.get(0).toString());

    }

}