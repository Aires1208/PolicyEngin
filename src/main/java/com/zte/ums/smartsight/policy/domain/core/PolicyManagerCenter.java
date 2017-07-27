package com.zte.ums.smartsight.policy.domain.core;

import com.zte.ums.smartsight.policy.domain.core.action.ActionFactory;
import com.zte.ums.smartsight.policy.domain.core.cache.PolicyCache;
import com.zte.ums.smartsight.policy.domain.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by aires on 2016/9/26.
 */
@Component
public class PolicyManagerCenter {

    private static final Logger LOGGER = LoggerFactory.getLogger(PolicyManagerCenter.class);

    @Resource
    private PolicyCache cache;

    @Autowired
    private PolicyActionRepo policyActionRepo;

    public void trigger(ResultEvent event) {
        try {
            Policy policy = getPolicyByEvent(event);
            List<PolicyAction> policyActions = newArrayList();
            for (String policyActionName : policy.getPolicyActions()) {
                policyActions.add(policyActionRepo.getPolicyActionByPolicyName(policyActionName));
            }
            ActionFactory.triggerAction(policyActions, event);
        } catch (PolicyException e) {
            LOGGER.error(e.getMessage(), e);
        }
    }

    private Policy getPolicyByEvent(ResultEvent event) {
        Policy eventPolicy = null;
        Map<String, Policy> policyCachedMap = cache.getCache();
        Iterator iterator = policyCachedMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Policy> entry = (Map.Entry<String, Policy>) iterator.next();
            Policy policy = entry.getValue();
            if (policy.getCondition().getObjDN().equalsIgnoreCase(event.getObjDN())) {
                List<String> eventTypesCode = newArrayList();
                for (String eventType : policy.getCondition().getEventTypes()) {
                    if (null != EventTypeMap.getEventTypeMap().get(eventType)) {
                        eventTypesCode.add(Integer.toString(EventTypeMap.getEventTypeMap().get(eventType)));
                    }
                }
                if (eventTypesCode.contains(event.getEventType())) {
                    eventPolicy = policy;
                    break;
                }
            }
        }
        if (null == eventPolicy) {
            throw new PolicyException(PolicyException.POLICY_NOT_EXISTS);
        }
        return eventPolicy;
    }

}
