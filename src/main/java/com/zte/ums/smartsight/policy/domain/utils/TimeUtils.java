package com.zte.ums.smartsight.policy.domain.utils;

public class TimeUtils {

    public static long reverseTimeMillis(long currentTimeMillis) {
        return Long.MAX_VALUE - currentTimeMillis;
    }


    public static long recoveryTimeMillis(long reverseCurrentTimeMillis) {
        return Long.MAX_VALUE - reverseCurrentTimeMillis;
    }
}
