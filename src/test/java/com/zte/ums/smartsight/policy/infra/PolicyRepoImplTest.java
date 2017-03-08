package com.zte.ums.smartsight.policy.infra;

import com.zte.ums.smartsight.policy.domain.constant.PolicyConstant;
import com.zte.ums.smartsight.policy.domain.model.Condition;
import com.zte.ums.smartsight.policy.domain.model.EventType;
import com.zte.ums.smartsight.policy.domain.model.Policy;
import com.zte.ums.smartsight.policy.domain.model.PolicyRepo;
import com.zte.ums.smartsight.policy.domain.utils.JsonUtils;
import org.apache.hadoop.hbase.KeyValue;
import org.apache.hadoop.hbase.client.Scan;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.data.hadoop.hbase.ResultsExtractor;
import org.springframework.data.hadoop.hbase.RowMapper;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;
import static org.mockito.Mockito.*;

/**
 * Created by root on 11/9/16.
 * //
 */
//@RunWith(.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PolicyRepoImplTest {
    @InjectMocks
    PolicyRepo policyRepo = new PolicyRepoImpl();
    @Mock
    private HbaseTemplate hbaseTemplate;
    private Policy policy;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        policy = new Policy();
        policy.setPolicyName("APP_CALL_HEAVY");
        policy.setPolicyActions(newArrayList("Mail", "SMS", "UI"));
        Condition condition = new Condition();
        condition.setObjDN("app=ems");
        condition.setEventTypes(newArrayList(EventType.APP_CALLHEAVY_CRITICAL.getErrorName(), EventType.APP_ERRORHEAVY_CRITICAL.getErrorName()));
        policy.setCondition(condition);
    }

    @Test
    public void putPolicy() throws Exception {
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return "add success";
            }
        }).when(hbaseTemplate).put(PolicyConstant.POLICY, policy.getCondition().getObjDN(), PolicyConstant.POLICY_CF, policy.getPolicyName(), JsonUtils.serialize(policy).getBytes());

        policyRepo.putPolicy(policy);

        verify(hbaseTemplate, times(1)).put(PolicyConstant.POLICY, policy.getCondition().getObjDN(), PolicyConstant.POLICY_CF, policy.getPolicyName(), JsonUtils.serialize(policy).getBytes());
    }

    @Test
    public void deletePolicy() throws Exception {
        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return "delete success";
            }
        }).when(hbaseTemplate).delete(PolicyConstant.POLICY, policy.getCondition().getObjDN(), PolicyConstant.POLICY_CF, policy.getPolicyName());
        policyRepo.deletePolicy(policy);
        verify(hbaseTemplate, times(1)).delete(PolicyConstant.POLICY, policy.getCondition().getObjDN(), PolicyConstant.POLICY_CF, policy.getPolicyName());
    }

    @Test
    public void should_be_return_one_policy_when_call_queryPolices() throws Exception {

        List<Policy> policyList = newArrayList(policy);
        when(hbaseTemplate.find(Matchers.eq(PolicyConstant.POLICY), Matchers.any(Scan.class), Matchers.any(ResultsExtractor.class))).thenReturn(policyList);
        List<Policy> policies = policyRepo.queryPolices();
        Assert.assertEquals(1, policies.size());
    }

    @Test
    public void should_be_return_policy_and_objdn_is_app_ems_when_call_queryPoliciesByObjDN() throws Exception {
        List<Policy> policyList = newArrayList(policy);
        when(hbaseTemplate.get(Matchers.eq(PolicyConstant.POLICY), Matchers.eq("app=ems"), Matchers.any(RowMapper.class))).thenReturn(policyList);
        List<Policy> test = policyRepo.queryPoliciesByObjDN("app=ems");
        verify(hbaseTemplate, times(1)).get(Matchers.eq(PolicyConstant.POLICY), Matchers.eq("app=ems"), Matchers.any(RowMapper.class));

        Assert.assertEquals("APP_CALL_HEAVY", test.get(0).getPolicyName());

    }

    @Test
    public void should_be_return_policy_list() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        PolicyRepoImpl policyRepoImpl = new PolicyRepoImpl();
        Method method = policyRepoImpl.getClass().getDeclaredMethod("addEachPolicy", List.class, List.class);
        method.setAccessible(true);
//        Cell cell = new KeyValue(Bytes.toBytes("app=ems/E:10010/1476781649985/Put/vlen=139/seqid=0"));
//
        try {
            method.invoke(policyRepoImpl, newArrayList(new KeyValue()), newArrayList(policy));
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
//        when(method.invoke(policyRepoImpl)).thenReturn(newArrayList(policy));

//        ConsumerConnector consumerConnector = (ConsumerConnector) method.invoke(resultEventConsumerTest);
//        Assert.assertNotNull(consumerConnector);
    }
}