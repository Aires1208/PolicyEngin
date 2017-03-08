package com.zte.ums.smartsight.policy.domain.core.action;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;
import com.zte.ums.smartsight.policy.domain.model.ActionTypeEnum;
import com.zte.ums.smartsight.policy.domain.model.PolicyAction;
import com.zte.ums.smartsight.policy.domain.model.ResultEvent;

import java.util.List;
import java.util.Map;

/**
 * Created by 10172605 on 2016/9/26.
 */
public class ActionFactory {

    private static Map<ActionTypeEnum, BaseAction> actionMaps = ImmutableMap.of(
            ActionTypeEnum.MAIL, new MailAction(),
            ActionTypeEnum.SMS, new SMSAction(),
            ActionTypeEnum.UI, new DirectUIAction()
    );

    public static void triggerAction(List<PolicyAction> policyActions, ResultEvent resultEvent) {
        for (PolicyAction policyAction : policyActions) {
            Optional.fromNullable(actionMaps.get(policyAction.getActionTypeEnum())).or(new NullableAction()).execute(policyAction, resultEvent);
        }
    }
}
