package com.zte.ums.smartsight.policy.domain.model;

import java.util.List;

/**
 * Created by aires on 2016/9/26.
 */
public interface PolicyRepo {
    void putPolicy(Policy policy);

    void deletePolicy(Policy policy);

    List<Policy> queryPolices();

    List<Policy> queryPoliciesByObjDN(String objDN);
}
