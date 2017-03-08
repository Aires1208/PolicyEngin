package com.zte.ums.smartsight.policy.domain.core;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * Created by 10172605 on 2016/9/27.
 */
@Component
@Configurable
@EnableScheduling
public class PolicySchedule {

    @Autowired
    private ResultEventConsumer resultEventConsumer;

    @Scheduled(cron = "10/59 * * * * *")
    public void executePolicyByScanEvent() {
        System.out.println("==============================>");
        resultEventConsumer.consume();
    }
}