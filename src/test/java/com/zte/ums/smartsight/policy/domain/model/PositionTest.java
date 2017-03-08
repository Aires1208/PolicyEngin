package com.zte.ums.smartsight.policy.domain.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by root on 11/14/16.
 */
public class PositionTest {
    Position position = new Position(true);

    @Before
    public void setup() {

        position.setPolicyName("APP_CALL_HEAVY");

    }

    @Test
    public void isExist() throws Exception {
        Assert.assertTrue(position.isExist());
    }

    @Test
    public void toStringTest() throws Exception {
        Assert.assertEquals("Position{isExist=true, policyName='APP_CALL_HEAVY'}", position.toString());
    }

}