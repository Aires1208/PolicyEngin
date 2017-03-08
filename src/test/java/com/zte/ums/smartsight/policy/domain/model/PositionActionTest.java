package com.zte.ums.smartsight.policy.domain.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by root on 11/10/16.
 */
public class PositionActionTest {
    PositionAction positionAction = new PositionAction();

    @Before
    public void setUp() throws Exception {
        positionAction.setExist(true);
        positionAction.setPolicyActionName("test");

    }

    @Test
    public void isExist() throws Exception {
        Assert.assertTrue(positionAction.isExist());

    }

    @Test
    public void setExist() throws Exception {
        positionAction.setExist(true);
    }

    @Test
    public void getPolicyActionName() throws Exception {
        Assert.assertEquals("test", positionAction.getPolicyActionName());
    }

    @Test
    public void setPolicyActionName() throws Exception {
        positionAction.setPolicyActionName("test");
    }

}