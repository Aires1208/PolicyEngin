package com.zte.ums.smartsight.policy.domain.model;

import com.zte.ums.smartsight.policy.domain.constant.EnvConstant;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Created by root on 11/14/16.
 */
public class MailSenderInfoTest {
    MailSenderInfo mailInfo = new MailSenderInfo();

    @Before
    public void setup() {
        mailInfo.setMailServerHost(EnvConstant.MAIL_SERVER_HOST);
        mailInfo.setMailServerPort(EnvConstant.MAIL_SERVER_PORT);
        mailInfo.setValidate(true);
        mailInfo.setUserName(EnvConstant.MAIL_SERVER_USER_NAME);   //自己的邮箱
        mailInfo.setPassword(EnvConstant.MAIL_SERVER_PASSWORD);//自己的邮箱密码，用于验证

        mailInfo.setFromAddress("zhang.pei162@zte.com.cn");  ///自己的邮箱
//        mailInfo.setToAddress("10172605@zte.com.cn");   ///对方的邮箱
        mailInfo.setToAddress("zhang.pei162@zte.com.cn");   ///对方的邮箱
        mailInfo.setSubject("Mail sender ut test");
        mailInfo.setContent("Mail sender ut test");
    }

    @Test
    public void getMailServerHost() throws Exception {
        Assert.assertEquals("10.62.54.20", mailInfo.getMailServerHost());

    }

    @Test
    public void getMailServerPort() throws Exception {
        Assert.assertEquals("25", mailInfo.getMailServerPort());
    }

}