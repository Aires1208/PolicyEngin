package com.zte.ums.smartsight.policy.domain.core;

import kafka.javaapi.consumer.ConsumerConnector;
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

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Created by root on 11/10/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)

public class PolicyScheduleTest {
    @InjectMocks
    PolicySchedule policySchedule = new PolicySchedule();
    @Mock
    private ResultEventConsumer resultEventConsumer;
    @Mock
    private ConsumerConnector consumerConnector;
    @Mock
    private ResultEventHandler resultEventHandler;

    @Before
    public void setUp() throws Exception {

    }

    @Test
    public void should_be_trigger_schedule_task_executePolicyByScanEvent() throws Exception {

        Mockito.doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return "calling.........";
            }
        }).when(resultEventConsumer).consume();
        policySchedule.executePolicyByScanEvent();

        verify(resultEventConsumer, times(1)).consume();

    }

}