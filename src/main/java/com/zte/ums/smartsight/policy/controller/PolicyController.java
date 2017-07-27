package com.zte.ums.smartsight.policy.controller;

import com.zte.ums.smartsight.policy.domain.model.Policy;
import com.zte.ums.smartsight.policy.domain.model.PolicyAction;
import com.zte.ums.smartsight.policy.domain.model.Result;
import com.zte.ums.smartsight.policy.domain.model.ResultCommonInfo;
import com.zte.ums.smartsight.policy.domain.service.PolicyActionService;
import com.zte.ums.smartsight.policy.domain.service.PolicyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by aires on 2016/9/26.
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class PolicyController {
    @Autowired
    private PolicyService policyService;

    @Autowired
    private PolicyActionService policyActionService;

    @RequestMapping(value = "/policy/commonInfo", method = {RequestMethod.GET})
    public ResultCommonInfo queryCommonInfo() {
        return policyService.getCommonInfo();
    }

    @RequestMapping(value = "/policy", method = {RequestMethod.POST})
    public Result addPolicy(@RequestBody Policy policy) {
        return policyService.addPolicy(policy);
    }

    @RequestMapping(value = "/policy", method = {RequestMethod.DELETE})
    public Result deletePolicy(@RequestParam(value = "policyName") String policyName) {
        return policyService.deleteByPolicyName(policyName);
    }

    @RequestMapping(value = "/policy", method = {RequestMethod.PUT})
    public Result updatePolicy(@RequestBody Policy policy) {
        return policyService.updatePolicy(policy);
    }

    //    @RequestMapping(value = "/policy", method = {RequestMethod.GET})
//    public Result queryPolicyByAppName(@RequestParam(value = "appName") String appName) {
//        return policyService.getPolicyByAppName(appName);
//    }
    @RequestMapping(value = "/policy", method = {RequestMethod.GET})
    public Result queryPolicy() {
        return policyService.getPolicies();
    }


    @RequestMapping(value = "/policy/policyAction", method = {RequestMethod.POST})
    public Result addPolicyAction(@RequestBody PolicyAction policyAction) {
        return policyActionService.addPolicyAction(policyAction);
    }

    @RequestMapping(value = "/policy/policyAction", method = {RequestMethod.DELETE})
    public Result deleteByPolicyActionName(@RequestParam(value = "policyActionName") String policyActionName) {
        return policyActionService.deleteByPolicyActionName(policyActionName);
    }

    @RequestMapping(value = "/policy/policyAction", method = {RequestMethod.PUT})
    public Result updatePolicyAction(@RequestBody PolicyAction policyAction) {
        return policyActionService.updatePolicyAction(policyAction);
    }

    @RequestMapping(value = "/policy/policyAction", method = {RequestMethod.GET})
    public Result getPolicyActions() {
        return policyActionService.getPolicyActions();
    }
}
