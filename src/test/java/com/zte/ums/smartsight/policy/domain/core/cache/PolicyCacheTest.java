package com.zte.ums.smartsight.policy.domain.core.cache;

import com.google.common.collect.Maps;
import com.zte.ums.smartsight.policy.domain.model.Condition;
import com.zte.ums.smartsight.policy.domain.model.EventType;
import com.zte.ums.smartsight.policy.domain.model.Policy;
import com.zte.ums.smartsight.policy.domain.model.Position;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static com.google.common.collect.Lists.newArrayList;

/**
 * Created by root on 11/8/16.
 */
public class PolicyCacheTest {
    private Map<String, Policy> cache = Maps.newConcurrentMap();

    private PolicyCache policyCache = new PolicyCache();

    @Before
    public void setUp() throws Exception {

        policyCache.evictCache();

        Policy policy = new Policy();
        policy.setPolicyName("APP_CALL_HEAVY");
        policy.setPolicyActions(newArrayList("Mail", "SMS", "UI"));
        Condition condition = new Condition();
        condition.setObjDN("app=ems");
        condition.setEventTypes(newArrayList(EventType.APP_CALLHEAVY_CRITICAL.getErrorName(), EventType.APP_ERRORHEAVY_CRITICAL.getErrorName()));
        policy.setCondition(condition);

        Policy policy1 = new Policy();
        policy1.setPolicyName("APP_ERROR_HEAVY");
        policy1.setPolicyActions(newArrayList("Mail", "UI"));
        Condition condition1 = new Condition();
        condition1.setObjDN("app=ems");
        condition1.setEventTypes(newArrayList(EventType.APP_CALLHEAVY_CRITICAL.getErrorName(), EventType.APP_ERRORHEAVY_CRITICAL.getErrorName()));
        policy1.setCondition(condition1);

        policyCache.put(policy);
        policyCache.put(policy1);

        cache = policyCache.getCache();

    }

    @Test
    public void should_be_return_policy_from_cache_when_by_policyname_get() throws Exception {
        Assert.assertEquals("APP_ERROR_HEAVY", policyCache.get("APP_ERROR_HEAVY").getPolicyName());

    }

    @Test
    public void should_be_remove_policy_by_name_when_call_remove() throws Exception {
        Policy policy1 = new Policy();
        policy1.setPolicyName("APP_ERROR_HEAVY");
        policy1.setPolicyActions(newArrayList("Mail", "UI"));
        Condition condition1 = new Condition();
        condition1.setObjDN("app=ems");
        condition1.setEventTypes(newArrayList(EventType.APP_CALLHEAVY_CRITICAL.getErrorName(), EventType.APP_ERRORHEAVY_CRITICAL.getErrorName()));
        policy1.setCondition(condition1);

        policyCache.remove("APP_ERROR_HEAVY");
        Assert.assertEquals(1, cache.size());
        System.out.println(cache.size());
        policyCache.put(policy1);

        policyCache.remove("APP");
        Assert.assertEquals(2, cache.size());

    }

    @Test
    public void should_be_return_true_when_policy_exist_in_cache_isEventTypesExistByPolicyName() throws Exception {
        Policy policy = new Policy();
        policy.setPolicyName("APP_CALL_HEAVY");
        policy.setPolicyActions(newArrayList("Mail", "SMS", "UI"));
        Condition condition = new Condition();
        condition.setObjDN("app=ems");
        condition.setEventTypes(newArrayList(EventType.APP_CALLHEAVY_CRITICAL.getErrorName(), EventType.APP_ERRORHEAVY_CRITICAL.getErrorName()));
        policy.setCondition(condition);

        Assert.assertTrue(policyCache.isEventTypesExistByPolicyName(policy).isExist());
    }

    @Test
    public void isEventTypesExistByPolicyName_is_false() throws Exception {
        Policy policy = new Policy();
        policy.setPolicyName("APP_CALL");
        policy.setPolicyActions(newArrayList("Mail", "SMS", "UI"));
        Condition condition = new Condition();
        condition.setObjDN("appms");
        condition.setEventTypes(newArrayList(EventType.APP_CALLHEAVY_CRITICAL.getErrorName(), EventType.APP_ERRORHEAVY_CRITICAL.getErrorName()));
        policy.setCondition(condition);

        Assert.assertFalse(policyCache.isEventTypesExistByPolicyName(policy).isExist());
    }

    @Test
    public void isPolicyExist_is_true() throws Exception {
        Policy policy = new Policy();
        policy.setPolicyName("APP_CALL_HEAVY");
        policy.setPolicyActions(newArrayList("Mail", "SMS", "UI"));
        Condition condition = new Condition();
        condition.setObjDN("app=ems");
        condition.setEventTypes(newArrayList(EventType.APP_CALLHEAVY_CRITICAL.getErrorName(), EventType.APP_ERRORHEAVY_CRITICAL.getErrorName()));
        policy.setCondition(condition);
        Assert.assertTrue(policyCache.isPolicyExist(policy).isExist());
    }

    @Test
    public void isPolicyExist_is_false() throws Exception {
        Policy policy = new Policy();
        policy.setPolicyName("APP_HEAVY");
        policy.setPolicyActions(newArrayList("Mail", "SMS", "UI"));
        Condition condition = new Condition();
        condition.setObjDN("app");
        condition.setEventTypes(newArrayList(EventType.APP_CALLHEAVY_CRITICAL.getErrorName(), EventType.APP_ERRORHEAVY_CRITICAL.getErrorName()));
        policy.setCondition(condition);
        Assert.assertNull(policyCache.isPolicyExist(policy).getPolicyName());
    }

    @Test
    public void should_be_return_cache_position_when_call_getCachePosition_with_arg_policy() {
        Policy policy = new Policy();
        policy.setPolicyName("APP_CALL_HEAVY");
        policy.setPolicyActions(newArrayList("Mail", "SMS", "UI"));
        Condition condition = new Condition();
        condition.setObjDN("app=ems");
        condition.setEventTypes(newArrayList(EventType.APP_CALLHEAVY_CRITICAL.getErrorName(), EventType.APP_ERRORHEAVY_CRITICAL.getErrorName()));
        policy.setCondition(condition);

        Position position = policyCache.getCachePosition(policy);
        Assert.assertEquals("Position{isExist=true, policyName='APP_CALL_HEAVY'}", position.toString());
    }

    @Test
    public void toStringTest() {
//        System.out.println();
        Assert.assertEquals("PolicyCache{cache={APP_CALL_HEAVY=Policy{policyName='APP_CALL_HEAVY', condition=Condition{objDN='app=ems', eventTypes=[APP_CALLHEAVY_CRITICAL, APP_ERRORHEAVY_CRITICAL]}, policyActionsNames=[Mail, SMS, UI]}, APP_ERROR_HEAVY=Policy{policyName='APP_ERROR_HEAVY', condition=Condition{objDN='app=ems', eventTypes=[APP_CALLHEAVY_CRITICAL, APP_ERRORHEAVY_CRITICAL]}, policyActionsNames=[Mail, UI]}}}", policyCache.toString());
    }

    @Test
    public void should_be_return_null_when_call_getCachePosition_with_policycache_not_exist() {
        Policy policy = new Policy();
        policy.setPolicyName("APP_CALL_HEAVY");
        policy.setPolicyActions(newArrayList("Mail", "SMS", "UI"));
        Condition condition = new Condition();
        condition.setObjDN("app=ems");
        condition.setEventTypes(newArrayList(EventType.APP_CALLHEAVY_CRITICAL.getErrorName(), EventType.APP_ERRORHEAVY_CRITICAL.getErrorName()));
        policy.setCondition(condition);
//        Map<String, Policy> testcache = Maps.newConcurrentMap();
        policyCache.remove("APP_CALL_HEAVY");
        policyCache.remove("APP_ERROR_HEAVY");
        System.out.println(cache.size());

        Position position = policyCache.getCachePosition(policy);
        Assert.assertEquals("Position{isExist=false, policyName='null'}", position.toString());
    }

}