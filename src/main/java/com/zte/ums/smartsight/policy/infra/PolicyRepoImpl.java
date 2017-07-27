package com.zte.ums.smartsight.policy.infra;

import com.zte.ums.smartsight.policy.domain.constant.PolicyConstant;
import com.zte.ums.smartsight.policy.domain.model.Policy;
import com.zte.ums.smartsight.policy.domain.model.PolicyRepo;
import com.zte.ums.smartsight.policy.domain.utils.JsonUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.util.Bytes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by aires on 2016/9/26.
 */
@Repository
public class PolicyRepoImpl implements PolicyRepo {

    @Autowired
    private HbaseTemplate hbaseTemplate;

    /**
     * insert and update policy
     */
    @Override
    public void putPolicy(Policy policy) {
        hbaseTemplate.put(PolicyConstant.POLICY, policy.getCondition().getObjDN(), PolicyConstant.POLICY_CF, policy.getPolicyName(), JsonUtils.serialize(policy).getBytes());
    }

    /**
     * delete policy
     */
    @Override
    public void deletePolicy(Policy policy) {
        hbaseTemplate.delete(PolicyConstant.POLICY, policy.getCondition().getObjDN(), PolicyConstant.POLICY_CF, policy.getPolicyName());
    }


    /**
     * get all policies
     */
    @Override
    public List<Policy> queryPolices() {
        return hbaseTemplate.find(PolicyConstant.POLICY, new Scan(), resultScanner -> {
            List<Policy> policies = newArrayList();
            for (Result result : resultScanner) {
                List<Cell> cells = result.listCells();
                addEachPolicy(cells, policies);
            }
            return policies;
        });
    }

    /**
     * get  policies by objDN
     */
    @Override
    public List<Policy> queryPoliciesByObjDN(String objDN) {
        return hbaseTemplate.get(PolicyConstant.POLICY, objDN, (result, rowNum) -> {
            List<Policy> policies = newArrayList();
            List<Cell> cells = result.listCells();
            addEachPolicy(cells, policies);
            return policies;
        });
    }

    private void addEachPolicy(List<Cell> cells, List<Policy> policies) {
        for (Cell cell : cells) {
            if (null != cell.getValue()) {
                Policy policy = JsonUtils.deserialize(Bytes.toString(cell.getValue()), Policy.class);
                policies.add(policy);
            }
        }
    }
}
