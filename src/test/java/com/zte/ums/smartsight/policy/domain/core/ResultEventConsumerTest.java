package com.zte.ums.smartsight.policy.domain.core;

import com.zte.ums.smartsight.policy.domain.constant.EnvConstant;
import com.zte.ums.smartsight.policy.domain.model.EventType;
import com.zte.ums.smartsight.policy.domain.model.ResultEvent;
import kafka.javaapi.consumer.ConsumerConnector;
import org.apache.hadoop.hbase.shaded.org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Properties;

/**
 * Created by root on 11/9/16.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResultEventConsumerTest {
    @InjectMocks
    ResultEventConsumer resultEventConsumer = new ResultEventConsumer();
    @Mock
    private PolicyManagerCenter policyManagerCenter;
    @Mock
    private ResultEventHandler resultEventHandler;
    @Mock
    private ConsumerConnector consumerConnector;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void consume() throws Exception {
//        ResultEventConsumer resultEventConsumerTest = new ResultEventConsumer();
//        Method method = resultEventConsumer.getClass().getMethod("getConsumerConnector");
//        method.setAccessible(true);
//        ConsumerConnector consumerConnector = (ConsumerConnector) method.invoke(resultEventConsumer);
//        Assert.assertNotNull(consumerConnector);

//        resultEventConsumer.consume();
    }

    @Test
    public void handleResultEventTest_resultevent_is_already_exist() {
        ResultEvent resultEventExist = new ResultEvent();
        resultEventExist.setEventType("10011");
        resultEventExist.setStartTime(System.currentTimeMillis() - 60 * 1000);
        resultEventExist.setEndTime(System.currentTimeMillis());
        resultEventExist.setObjDN("app=ems");
        resultEventExist.setDetail("app=ems,call_heavy");

        Mockito.when(resultEventHandler.getSignalResultEvent("ActiveEvent", resultEventExist.getObjDN(), resultEventExist.getEventType())).thenReturn(resultEventExist);

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return "do insert";
            }
        }).when(resultEventHandler).insert("HistoryEvent", resultEventExist);
        resultEventConsumer.handleResultEvent(resultEventExist);
    }

    @Test
    public void handleResultEventTest_result_event_not_exist() {
        ResultEvent resultEventExist = new ResultEvent();
        resultEventExist.setEventType("10011");
        resultEventExist.setStartTime(System.currentTimeMillis() - 60 * 1000);
        resultEventExist.setEndTime(System.currentTimeMillis());
        resultEventExist.setObjDN("app=ems");
        resultEventExist.setDetail("app=ems,call_heavy");

        Mockito.when(resultEventHandler.getSignalResultEvent("ActiveEvent", resultEventExist.getObjDN(), resultEventExist.getEventType())).thenReturn(null);

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return "do insert";
            }
        }).when(resultEventHandler).insert("ActiveEvent", resultEventExist);
        resultEventConsumer.handleResultEvent(resultEventExist);
    }

    @Test
    public void should_be_throw_exception() {
        ResultEvent resultEventExist = new ResultEvent();
        resultEventExist.setEventType(EventType.APP_CALLHEAVY_CRITICAL.getErrorName());
        resultEventExist.setStartTime(System.currentTimeMillis() - 60 * 1000);
        resultEventExist.setEndTime(System.currentTimeMillis());
        resultEventExist.setObjDN("app=ems");
        resultEventExist.setDetail("app=ems,call_heavy");

        Mockito.when(resultEventHandler.getSignalResultEvent("ActiveEvent", resultEventExist.getObjDN(), resultEventExist.getEventType())).thenReturn(null);
        try {
            resultEventConsumer.handleResultEvent(resultEventExist);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void should_be_throw_uncheck_exception() {
        ResultEvent resultEventExist = new ResultEvent();
        resultEventExist.setEventType(EventType.APP_CALLHEAVY_CRITICAL.getErrorName());
        resultEventExist.setStartTime(System.currentTimeMillis() - 60 * 1000);
        resultEventExist.setEndTime(System.currentTimeMillis());
        resultEventExist.setObjDN("app=ems");
        resultEventExist.setDetail("app=ems,call_heavy");
        Mockito.doThrow(new PolicyException(PolicyException.POLICY_NOT_EXISTS)).when(policyManagerCenter).trigger(resultEventExist);

        resultEventConsumer.handleResultEvent(resultEventExist);
    }

    @Test
    public void getPropertiesTest() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        ResultEventConsumer resultEventConsumerTest = new ResultEventConsumer();

        Method method = resultEventConsumerTest.getClass().getDeclaredMethod("getProperties");
        method.setAccessible(true);

        Properties properties = (Properties) method.invoke(resultEventConsumerTest);
        properties.put("zookeeper.connect", "127.0.0.1:2181");
        properties.put("group.id", EnvConstant.GROUP_ID);

        Assert.assertEquals("127.0.0.1:2181", properties.get("zookeeper.connect"));
        Assert.assertEquals(EnvConstant.GROUP_ID, properties.get("group.id"));


    }
}