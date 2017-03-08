package com.zte.ums.smartsight.policy.domain.core.cache;

import com.zte.ums.smartsight.policy.domain.model.Condition;
import com.zte.ums.smartsight.policy.domain.model.EventType;
import com.zte.ums.smartsight.policy.domain.model.Policy;
import com.zte.ums.smartsight.policy.domain.model.PolicyRepo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static com.google.common.collect.Lists.newArrayList;
import static org.mockito.Mockito.when;

/**
 * Created by root on 11/11/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class InitializationCacheTest {
    @InjectMocks
    InitializationCache initializationCache = new InitializationCache();
    @Mock
    private PolicyCache policyCache;
    @Mock
    private PolicyRepo policyRepo;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void should_be_init_policy_cache_initialCache() throws Exception {
        Policy policy = new Policy();

        policy.setPolicyName("APP_CALL_HEAVY");
        policy.setPolicyActions(newArrayList("Mail", "SMS", "UI"));
        Condition condition = new Condition();
        condition.setObjDN("app=ems");
        condition.setEventTypes(newArrayList(EventType.APP_CALLHEAVY_CRITICAL.getErrorName(), EventType.APP_ERRORHEAVY_CRITICAL.getErrorName()));
        policy.setCondition(condition);

//         List<Policy> policies = policyRepo.queryPolices();
//        for (Policy policy : policies) {
//            policyCache.put(policy);
//        }
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return "add success";
            }
        }).when(policyCache).put(policy);

        when(policyRepo.queryPolices()).thenReturn(newArrayList(policy));
        initializationCache.initialCache();
//        verify(hbaseTemplate,times(1)).put(PolicyConstant.POLICY_ACTION, policyAction.getPolicyActionName(), PolicyConstant.POLICY_ACTUON_CF, policyAction.getPolicyActionName(), JsonUtils.serialize(policyAction).getBytes());

    }

}