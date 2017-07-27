package com.zte.ums.smartsight.policy.domain.model;

import com.zte.ums.smartsight.policy.domain.constant.EnvConstant;
import org.junit.Test;

/**
 * Created by root on 11/8/16.
 */
public class MailSenderTest {
    @Test
    public void should_send_a_text_mail() throws Exception {

        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost(EnvConstant.MAIL_SERVER_HOST);
        mailInfo.setMailServerPort(EnvConstant.MAIL_SERVER_PORT);
        mailInfo.setValidate(true);
        mailInfo.setUserName(EnvConstant.MAIL_SERVER_USER_NAME);
        mailInfo.setPassword(EnvConstant.MAIL_SERVER_PASSWORD);//自己的邮箱密码，用于验证

        mailInfo.setFromAddress("zhang.pei162@zte.com.cn");  ///自己的邮箱
//        mailInfo.setToAddress("aires@zte.com.cn");   ///对方的邮箱
        mailInfo.setToAddress("zhang.pei162@zte.com.cn");   ///对方的邮箱
        mailInfo.setSubject("Mail sender ut test");
        mailInfo.setContent("Mail sender ut test");

        MailSender mailSender = new MailSender();
        mailSender.sendTextMail(mailInfo);
    }

    @Test
    public void should_send_a_text_mail_and_Validate_is_false() throws Exception {

        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost(EnvConstant.MAIL_SERVER_HOST);
        mailInfo.setMailServerPort(EnvConstant.MAIL_SERVER_PORT);
        mailInfo.setValidate(false);
        mailInfo.setUserName(EnvConstant.MAIL_SERVER_USER_NAME);
        mailInfo.setPassword(EnvConstant.MAIL_SERVER_PASSWORD);//自己的邮箱密码，用于验证

        mailInfo.setFromAddress("zhang.pei162@zte.com.cn");  ///自己的邮箱
//        mailInfo.setToAddress("aires@zte.com.cn");   ///对方的邮箱
        mailInfo.setToAddress("zhang.pei162@zte.com.cn");   ///对方的邮箱
        mailInfo.setSubject("Mail sender ut test");
        mailInfo.setContent("Mail sender ut test");

        MailSender mailSender = new MailSender();
        try {
            mailSender.sendTextMail(mailInfo);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

}