package com.zte.ums.smartsight.policy.domain.core.cache;

import com.google.common.collect.Maps;
import com.zte.ums.smartsight.policy.domain.model.Condition;
import com.zte.ums.smartsight.policy.domain.model.Policy;
import com.zte.ums.smartsight.policy.domain.model.Position;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by aires on 2016/9/26.
 */
@Component
public class PolicyCache {
    private Map<String, Policy> cache = Maps.newConcurrentMap();

    public Map<String, Policy> getCache() {
        return cache;
    }

    public Policy get(String key) {
        return cache.get(key);
    }

    public void put(Policy policy) {
        cache.put(policy.getPolicyName(), policy);
    }

    public void remove(String key) {
        if (cache.containsKey(key)) {
            cache.remove(key);
        }
    }

    public Position isEventTypesExistByPolicyName(Policy policy) {
        Position position = new Position();
//        for (String policyName : cache.keySet()) {
//            Condition cachedCondition = cache.get(policyName).getCondition();
//            Condition importCondition = policy.getCondition();
//            if ((!policy.getPolicyName().equalsIgnoreCase(policyName)) && cachedCondition.getObjDN().equalsIgnoreCase(importCondition.getObjDN())) {
//                if (setPosition(position, policyName, cachedCondition, importCondition)) break;
//            }
//        }

        Iterator iterator = cache.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Policy> entry = (Map.Entry<String, Policy>) iterator.next();
            Condition cachedCondition = entry.getValue().getCondition();
            Condition importCondition = policy.getCondition();
            String policyName = entry.getValue().getPolicyName();
            if ((!policy.getPolicyName().equalsIgnoreCase(policyName)) && cachedCondition.getObjDN().equalsIgnoreCase(importCondition.getObjDN())) {
                if (setPosition(position, policyName, cachedCondition, importCondition)) break;
            }
        }
        return position;
    }

    private boolean setPosition(Position position, String policyName, Condition cachedCondition, Condition importCondition) {
        List<String> cachedEventTypes = newArrayList();
        cachedEventTypes.addAll(cachedCondition.getEventTypes());
        List<String> importEventTypes = importCondition.getEventTypes();
        cachedEventTypes.retainAll(importEventTypes);
        if (!cachedEventTypes.isEmpty()) {
            position.setPolicyName(policyName);
            position.setExist(true);
            return true;
        }
        return false;
    }

    public Position isPolicyExist(Policy policy) {
        Position position = new Position();
        Iterator iterator = cache.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Policy> entry = (Map.Entry<String, Policy>) iterator.next();
            if (entry.getValue().getPolicyName().equalsIgnoreCase(policy.getPolicyName())) {
                position.setPolicyName(policy.getPolicyName());
                position.setExist(true);
                return position;
            }
//            Condition cachedCondition = entry.getValue().getCondition();
//            Condition importCondition = policy.getCondition();
//            String policyName = entry.getValue().getPolicyName();
//            if ((!policy.getPolicyName().equalsIgnoreCase(policyName)) && cachedCondition.getObjDN().equalsIgnoreCase(importCondition.getObjDN())) {
//                if (setPosition(position, policyName, cachedCondition, importCondition)) break;
//            }
        }
//
//        if (cache.containsKey(policy.getPolicyName())) {
//            position.setPolicyName(policy.getPolicyName());
//            position.setExist(true);
//            return position;
//        }
        position = getCachePosition(policy);
        return position;
    }

    public Position getCachePosition(Policy policy) {
        Position position = new Position();
        Iterator iterator = cache.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Policy> entry = (Map.Entry<String, Policy>) iterator.next();
            Condition cachedCondition = entry.getValue().getCondition();
            Condition importCondition = policy.getCondition();
            if (cachedCondition.getObjDN().equalsIgnoreCase(importCondition.getObjDN())) {
                if (setPosition(position, entry.getValue().getPolicyName(), cachedCondition, importCondition)) break;
            }
//        for (String policyName : cache.keySet()) {
//            Condition cachedCondition = cache.get(policyName).getCondition();
//            Condition importCondition = policy.getCondition();
//            if (cachedCondition.getObjDN().equalsIgnoreCase(importCondition.getObjDN())) {
//                if (setPosition(position, policyName, cachedCondition, importCondition)) break;
//            }
        }
        return position;
    }


    public void evictCache() {
        cache.clear();
    }

    @Override
    public String toString() {
        return "PolicyCache{" +
                "cache=" + cache +
                '}';
    }
}
