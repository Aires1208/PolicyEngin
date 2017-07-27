package com.zte.ums.smartsight.policy.domain.core.cache;

import com.zte.ums.smartsight.policy.domain.model.Policy;
import com.zte.ums.smartsight.policy.domain.model.PolicyRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by aires on 2016/9/27.
 */
@Component
public class InitializationCache {
    private static final Logger LOGGER = LoggerFactory.getLogger(InitializationCache.class);
    @Resource
    private PolicyCache policyCache;

    @Autowired
    private PolicyRepo policyRepo;

    public void initialCache() {
        policyCache.evictCache();
        List<Policy> policies = null;
        try {
            policies = policyRepo.queryPolices();
        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
        }
        for (Policy policy : policies) {
            policyCache.put(policy);
        }
        LOGGER.info("initialization cache success");
    }
}
