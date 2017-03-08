package com.zte.ums.smartsight.policy.domain.core;

import com.zte.ums.smartsight.policy.domain.constant.PolicyConstant;
import com.zte.ums.smartsight.policy.domain.model.EventType;
import com.zte.ums.smartsight.policy.domain.model.ResultEvent;
import com.zte.ums.smartsight.policy.domain.utils.JsonUtils;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.hadoop.hbase.HbaseTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.matchers.JUnitMatchers.containsString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.powermock.api.mockito.PowerMockito.when;

/**
 * Created by root on 11/9/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResultEventHandlerTest {
    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @InjectMocks
    ResultEventHandler resultEventHandler = new ResultEventHandler();
    @Autowired
    private TestRestTemplate restTemplate;
    @Mock
    private HbaseTemplate hbaseTemplate;
    private ResultEvent resultEvent;

    @Before
    public void setUp() throws Exception {
        resultEvent = new ResultEvent();
        resultEvent.setEventType(EventType.APP_CALLHEAVY_CRITICAL.getErrorName());
        resultEvent.setStartTime(System.currentTimeMillis() - 60 * 1000);
        resultEvent.setEndTime(System.currentTimeMillis());
        resultEvent.setObjDN("app=ems");
        resultEvent.setDetail("app=ems,call_heavy");
    }

    @Test
    public void privateMethodTest() {
//        this.
    }


    @Test
    public void should_be_insert_event_when_call_insert_with_resultevent() throws Exception {

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return "calling.........";
            }
        }).when(hbaseTemplate).put("ActiveEvent", "ems", PolicyConstant.EVENT_CF, String.valueOf(resultEvent.getEventType()), JsonUtils.serialize(resultEvent).getBytes());
        resultEventHandler.insert(PolicyConstant.ACTION_EVENT, resultEvent);

        verify(hbaseTemplate, times(1)).put("ActiveEvent", "app=ems", PolicyConstant.EVENT_CF, String.valueOf(resultEvent.getEventType()), JsonUtils.serialize(resultEvent).getBytes());
    }

    @Test
    public void should_be_throw_policy_not_exist_exception_insert() throws Exception {

        try {
            resultEventHandler.insert(PolicyConstant.ACTION_EVENT, null);
        } catch (PolicyException e) {
            Assert.assertThat(e.getMessage(), containsString("ResultEvent must be not null"));
        }
    }

    @Test
    public void should_be_throw_exception() throws Exception {
        ResultEvent resultEventEx = new ResultEvent();
        resultEventEx.setEventType(EventType.APP_CALLHEAVY_CRITICAL.getErrorName());
        resultEventEx.setStartTime(System.currentTimeMillis() - 60 * 1000);
        resultEventEx.setEndTime(System.currentTimeMillis());
//        resultEventEx.setObjDN("app=ems");
        resultEventEx.setDetail("app=ems,call_heavy");

        try {
            resultEventHandler.insert("Test", resultEventEx);
        } catch (PolicyException e) {
            Assert.assertThat(e.getMessage(), containsString("ResultEvent must be not null"));
        }
    }

    @Test
    public void should_be_insert_resultevent_to_activeEvent_table_test() throws Exception {
        ResultEvent resultEventEx = new ResultEvent();
        resultEventEx.setEventType(EventType.APP_CALLHEAVY_CRITICAL.getErrorName());
        resultEventEx.setStartTime(System.currentTimeMillis() - 60 * 1000);
        resultEventEx.setEndTime(System.currentTimeMillis());
        resultEventEx.setObjDN("app=ems");
        resultEventEx.setDetail("app=ems,call_heavy");

        try {
            resultEventHandler.insert(PolicyConstant.ACTION_EVENT, resultEventEx);
        } catch (PolicyException e) {
            Assert.assertThat(e.getMessage(), containsString("ResultEvent must be not null"));
        }
    }


    @Test
    public void should_be_return_signal_resultevent_when_call_getSignalResultEvent_with_objdn() throws Exception {
        when(hbaseTemplate.get(PolicyConstant.ACTION_EVENT, "app=ems", (result, rowNum) -> {
            ResultEvent resultEvent = new ResultEvent();
            String rowKey = Bytes.toString(result.getRow());
            byte[] resultEventBytes = result.getValue(Bytes.toBytes(PolicyConstant.EVENT_FAMILY_NAME), Bytes.toBytes(String.valueOf(EventType.APP_CALLHEAVY_CRITICAL.getErrorCode())));
            if (null != resultEventBytes && resultEventBytes.length > 0) {
                String resultEventJson = Bytes.toString(resultEventBytes);
                System.out.println(resultEventJson);
                resultEvent = JsonUtils.deserialize(resultEventJson, ResultEvent.class);
            }
            return resultEvent;
        })).thenReturn(resultEvent);

        resultEventHandler.getSignalResultEvent(PolicyConstant.ACTION_EVENT, "app=ems", String.valueOf(EventType.APP_CALLHEAVY_CRITICAL.getErrorCode()));


    }

    @Test
    public void getResultEventTest() {
        ResultEvent resultEvent = new ResultEvent();
        resultEvent.setEventType(EventType.APP_CALLHEAVY_CRITICAL.getErrorName());
        resultEvent.setStartTime(1479125209850l);
        resultEvent.setEndTime(1479125269850l);
        resultEvent.setObjDN("app=ems");
        resultEvent.setDetail("app=ems,call_heavy");

        Result result = new Result();
        result.setExists(true);
        resultEventHandler.getResultEvent("1", result);
    }

}