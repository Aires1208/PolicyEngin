package com.zte.ums.smartsight.policy.domain.utils;

import org.junit.Assert;
import org.junit.Test;


/**
 * Created by root on 11/7/16.
 */
public class TimeUtilsTest {
    TimeUtils timeUtils;

    @Test
    public void setup() {
        timeUtils = new TimeUtils();
    }

    @Test
    public void shoule_be_return_specify_value_when_call_reverseTimeMillis() throws Exception {
        Assert.assertEquals(9223370558340406596l, timeUtils.reverseTimeMillis(1478514369211l));
        //
    }

    @Test
    public void shoule_be_return_specify_value_when_call_recoveryTimeMillis() throws Exception {
        Assert.assertEquals(9223370558340406596L, timeUtils.recoveryTimeMillis(1478514369211l));

    }

}