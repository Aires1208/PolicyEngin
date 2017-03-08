package com.zte.ums.smartsight.policy.domain.service;

import com.zte.ums.smartsight.policy.domain.core.PolicyException;
import com.zte.ums.smartsight.policy.domain.core.cache.PolicyCache;
import com.zte.ums.smartsight.policy.domain.model.*;
import com.zte.ums.smartsight.policy.domain.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;
import static com.zte.ums.smartsight.policy.domain.model.Result.FAIL;
import static com.zte.ums.smartsight.policy.domain.model.Result.SUCCESS;

/**
 * Created by 10172605 on 2016/9/26.
 */
@Service
public class PolicyServiceImpl implements PolicyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PolicyServiceImpl.class);
    @Resource
    private PolicyCache cache;

    @Autowired
    private PolicyRepo policyRepo;

    @Autowired
    private PolicyActionRepo policyActionRepo;

    @Override
    public Result addPolicy(Policy policy) {
        ResultBuilder resultBuilder = ResultBuilder.newResult();
        Position position = cache.isPolicyExist(policy);
        putPolicy(policy, resultBuilder, position);
        return resultBuilder.build();
    }

    @Override
    public Result getPolicyByAppName(String appName) {
        ResultBuilder resultBuilder = ResultBuilder.newResult();
        Policies policies = new Policies();
        List<Policy> policyList = newArrayList();
        try {
            Map<String, Policy> policyMaps = cache.getCache();

            Iterator iterator = policyMaps.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Policy> entry = (Map.Entry<String, Policy>) iterator.next();
                Policy policy = entry.getValue();
                if (appName.equalsIgnoreCase(policy.getCondition().getObjDN())) {
                    policyList.add(policy);
                }
            }

            policies.setPolicies(policyList);
            resultBuilder.data(JsonUtils.obj2JSON(policies));
        } catch (PolicyException e) {
            LOGGER.error(e.getMessage(), e);
            resultBuilder.status(FAIL);
            resultBuilder.message(e.getMessage() + "/" + String.valueOf(e));
        }
        return resultBuilder.build();
    }

    @Override
    public Result getPolicies() {
        ResultBuilder resultBuilder = ResultBuilder.newResult();
        Policies policies = new Policies();
        List<Policy> policyList = newArrayList();
        try {
            Map<String, Policy> policyMaps = cache.getCache();

            Iterator iterator = policyMaps.entrySet().iterator();
            while (iterator.hasNext()) {
                Map.Entry<String, Policy> entry = (Map.Entry<String, Policy>) iterator.next();
                Policy policy = entry.getValue();
                policyList.add(policy);
            }
            policies.setPolicies(policyList);
            resultBuilder.data(JsonUtils.obj2JSON(policies));
        } catch (PolicyException e) {
            LOGGER.error(e.getMessage(), e);
            resultBuilder.status(FAIL);
            resultBuilder.message(e.getMessage() + "/" + String.valueOf(e));
        }
        return resultBuilder.build();
    }


    @Override
    public Result deleteByPolicyName(String policyName) {
        ResultBuilder resultBuilder = ResultBuilder.newResult();
        try {
            if (!cache.getCache().keySet().contains(policyName)) {
                LOGGER.warn(policyName + " does not exists in cache");
                throw new PolicyException(PolicyException.POLICY_NOT_EXISTS);
            }
            policyRepo.deletePolicy(cache.get(policyName));
            cache.remove(policyName);
            resultBuilder.message("SUCCESS");
        } catch (PolicyException e) {
            LOGGER.error(e.getMessage(), e);
            resultBuilder.status(FAIL);
            resultBuilder.message(e.getMessage() + "/" + String.valueOf(e));
        }
        return resultBuilder.build();
    }

    @Override
    public Result updatePolicy(Policy policy) {
        ResultBuilder resultBuilder = ResultBuilder.newResult();
        Position position = cache.isEventTypesExistByPolicyName(policy);
        putPolicy(policy, resultBuilder, position);
        return resultBuilder.build();
    }

    private void putPolicy(Policy policy, ResultBuilder resultBuilder, Position position) {
        try {
            if (position.isExist()) {
                throw new PolicyException(PolicyException.POLICY_ALREADY_EXISTS);
            }
            policyRepo.putPolicy(policy);
            cache.put(policy);
            resultBuilder.data(JsonUtils.obj2JSON(policy));
            resultBuilder.status(SUCCESS);
            resultBuilder.message("SUCCESS");
        } catch (PolicyException e) {
            LOGGER.error(e.getMessage(), e);
            resultBuilder.status(FAIL);
            resultBuilder.data(JsonUtils.obj2JSON(position));
            resultBuilder.message(PolicyException.POLICY_ALREADY_EXISTS);
        }
    }

    @Override
    public ResultCommonInfo getCommonInfo() {
        ResultCommonInfo resultCommonInfo = new ResultCommonInfo();
        CommonInfo commonInfo = new CommonInfo();
        List<PolicyAction> policyActions = policyActionRepo.getPolicyActions();
        List<String> policyActionsNames = newArrayList();
        for (PolicyAction policyAction : policyActions) {
            policyActionsNames.add(policyAction.getPolicyActionName());
        }
        commonInfo.setPolicyActions(policyActionsNames);

        EventType[] eventTypes = EventType.values();
        commonInfo.setEventTypes(Arrays.asList(eventTypes));
        ResMsg resMsg = new ResMsg();
        resMsg.setResInfo("SUCCESS");
        commonInfo.setResMsg(resMsg);
        resultCommonInfo.setCommonInfo(JsonUtils.obj2JSON(commonInfo));
//        resultCommonInfo.("OK");
        return resultCommonInfo;
    }


}
