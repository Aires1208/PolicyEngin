package com.zte.ums.smartsight.policy.domain.service;

import com.zte.ums.smartsight.policy.domain.core.PolicyActionException;
import com.zte.ums.smartsight.policy.domain.model.*;
import com.zte.ums.smartsight.policy.domain.utils.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.zte.ums.smartsight.policy.domain.model.Result.FAIL;

/**
 * Created by 10172605 on 2016/9/26.
 */
@Service
public class PolicyActionServiceImpl implements PolicyActionService {

    private static final Logger LOGGER = LoggerFactory.getLogger(PolicyActionServiceImpl.class);

    @Autowired
    private PolicyActionRepo policyActionRepo;

    @Override
    public Result addPolicyAction(PolicyAction policyAction) {
        if (policyActionRepo.isPolicyActionExist(policyActionRepo.getPolicyActions(), policyAction.getPolicyActionName())) {
            throw new PolicyActionException(PolicyActionException.POLICY_ACTION_ALREADY_EXISTS);
        }
        ResultBuilder resultBuilder = ResultBuilder.newResult();
        putPolicyAction(policyAction, resultBuilder);
        return resultBuilder.build();
    }

    private void putPolicyAction(PolicyAction policyAction, ResultBuilder resultBuilder) {
        try {
            policyActionRepo.putPolicyAction(policyAction);
            resultBuilder.data(JsonUtils.obj2JSON(policyAction));
        } catch (PolicyActionException e) {
            LOGGER.error(e.getMessage(), e);
            resultBuilder.status(FAIL);
            PositionAction positionAction = new PositionAction();
            positionAction.setPolicyActionName(positionAction.getPolicyActionName());
            positionAction.setExist(true);
            resultBuilder.data(JsonUtils.obj2JSON(positionAction));
            resultBuilder.message(PolicyActionException.POLICY_ACTION_ALREADY_EXISTS);
        }
    }


    @Override
    public Result deleteByPolicyActionName(String policyActionName) {
        ResultBuilder resultBuilder = ResultBuilder.newResult();
//        try {
        if (!policyActionRepo.isPolicyActionExist(policyActionRepo.getPolicyActions(), policyActionName)) {
            LOGGER.warn(policyActionName + " does not exists in cache");
            resultBuilder.status(FAIL);
            resultBuilder.message(PolicyActionException.POLICY_ACTION_NOT_EXISTS);
//                throw new PolicyActionException(PolicyActionException.POLICY_ACTION_NOT_EXISTS);
        }
        policyActionRepo.deleteByPolicyActionName(policyActionName);
//        } catch (PolicyActionException e) {
//            LOGGER.error(e.getMessage(), e);
//            resultBuilder.status(FAIL);
//            resultBuilder.message(e.getMessage() + "/" + String.valueOf(e));
//        }
        return resultBuilder.build();
    }

    @Override
    public Result updatePolicyAction(PolicyAction policyAction) {
        if (!policyActionRepo.isPolicyActionExist(policyActionRepo.getPolicyActions(), policyAction.getPolicyActionName())) {
            throw new PolicyActionException(PolicyActionException.POLICY_ACTION_NOT_EXISTS);
        }
        ResultBuilder resultBuilder = ResultBuilder.newResult();
        putPolicyAction(policyAction, resultBuilder);
        return resultBuilder.build();
    }

    @Override
    public Result getPolicyActions() {
        ResultBuilder resultBuilder = ResultBuilder.newResult();
        try {
            PolicyActions policyActions = new PolicyActions();
            List<PolicyAction> _policyActions = policyActionRepo.getPolicyActions();
            policyActions.setPolicyActions(_policyActions);
            resultBuilder.data(JsonUtils.obj2JSON(policyActions));
        } catch (PolicyActionException e) {
            LOGGER.error(e.getMessage(), e);
            resultBuilder.status(FAIL);
            resultBuilder.message(e.getMessage() + "/" + String.valueOf(e));
        }
        return resultBuilder.build();
    }


}
