package com.zte.ums.smartsight.policy.domain.model;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by root on 11/10/16.
 */
public class ReceiverTest {
    Receiver receiver = new Receiver();

    @Before
    public void setUp() throws Exception {
        receiver.setName("test");
        receiver.setEmail("test@zte.com.cn");
        receiver.setPhoneNumber("123456798");

    }

    @Test
    public void getName() throws Exception {
        Assert.assertEquals("test", receiver.getName());
    }

    @Test
    public void setName() throws Exception {
        receiver.setName("test");
    }

    @Test
    public void getEmail() throws Exception {
        Assert.assertEquals("test@zte.com.cn", receiver.getEmail());
    }

    @Test
    public void setEmail() throws Exception {
        receiver.setEmail("test@zte.com.cn");
    }

    @Test
    public void getPhoneNumber() throws Exception {
        Assert.assertEquals("123456798", receiver.getPhoneNumber());
    }

    @Test
    public void setPhoneNumber() throws Exception {
        receiver.setPhoneNumber("123456798");
    }

}