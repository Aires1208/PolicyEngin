package com.zte.ums.smartsight.policy.infra;

import com.zte.ums.smartsight.policy.domain.constant.PolicyConstant;
import com.zte.ums.smartsight.policy.domain.model.PolicyAction;
import com.zte.ums.smartsight.policy.domain.model.PolicyActionRepo;
import com.zte.ums.smartsight.policy.domain.utils.JsonUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.ResultScanner;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by 10172605 on 2016/9/26.
 */
@Repository
public class PolicyActionRepoImpl implements PolicyActionRepo {

    @Autowired
    private HbaseTemplate hbaseTemplate;

    /**
     * insert and update policy
     */
    @Override
    public void putPolicyAction(PolicyAction policyAction) {
        deleteByPolicyActionName(policyAction.getPolicyActionName());
        hbaseTemplate.put(PolicyConstant.POLICY_ACTION, policyAction.getPolicyActionName(), PolicyConstant.POLICY_ACTUON_CF, policyAction.getPolicyActionName(), JsonUtils.serialize(policyAction).getBytes());
    }

    /**
     * delete policy
     */
    @Override
    public void deleteByPolicyActionName(String policyActionName) {
        hbaseTemplate.delete(PolicyConstant.POLICY_ACTION, policyActionName, PolicyConstant.POLICY_ACTUON_CF, policyActionName);
    }

    /**
     * get all policies
     */
    @Override
    public List<PolicyAction> getPolicyActions() {

        return hbaseTemplate.find(PolicyConstant.POLICY_ACTION, new Scan(), resultScanner -> {
            return getPolicyActions(resultScanner);
        });
    }

    public List<PolicyAction> getPolicyActions(ResultScanner resultScanner) {
        List<PolicyAction> policyActions = newArrayList();
        for (Result result : resultScanner) {
            List<Cell> cells = result.listCells();
            addEachPolicyAction(cells, policyActions);
        }
        return policyActions;
    }

    @Override
    public PolicyAction getPolicyActionByPolicyName(String policyActionName) {
        return hbaseTemplate.find(PolicyConstant.POLICY_ACTION, new Scan(), resultScanner -> {
            PolicyAction policyAction = null;
            for (Result result : resultScanner) {
                List<Cell> cells = result.listCells();
                for (Cell cell : cells) {
                    PolicyAction policyActionInCell = JsonUtils.deserialize(Bytes.toString(cell.getValue()), PolicyAction.class);
                    if (policyActionInCell.getPolicyActionName().equalsIgnoreCase(policyActionName)) {
                        policyAction = policyActionInCell;
                        break;
                    }
                }
            }
            return policyAction;
        });
    }

    private void addEachPolicyAction(List<Cell> cells, List<PolicyAction> policyActions) {
        for (Cell cell : cells) {
            PolicyAction policyAction = JsonUtils.deserialize(Bytes.toString(cell.getValue()), PolicyAction.class);
            policyActions.add(policyAction);
        }
    }

    @Override
    public boolean isPolicyActionExist(List<PolicyAction> _policyActions, String policyActionName) {
        for (PolicyAction _policyAction : _policyActions) {
            if (_policyAction.getPolicyActionName().equals(policyActionName))
                return true;
        }
        return false;
    }

}
