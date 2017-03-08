package com.zte.ums.smartsight.policy.domain.core.action;

import com.zte.ums.smartsight.policy.domain.constant.EnvConstant;
import com.zte.ums.smartsight.policy.domain.model.*;

import java.util.List;

/**
 * Created by 10172605 on 2016/9/26.
 */
public class MailAction implements BaseAction {
    private static String getEventLevelByEventType(String eventType) {
        int eventTypeCode = Integer.parseInt(eventType);
        int flag = eventTypeCode % 10;
        if (1 == flag) {
            return "WARNING";
        } else if (2 == flag) {
            return "CRITICAL";
        } else {
            return "NORMAL";
        }
    }

    @Override
    public void execute(PolicyAction policyAction, ResultEvent resultEvent) {
        MailSender sms = new MailSender();
        List<String> receiverInfo = policyAction.getReceiverInfo();
        String mailTitle = "alarm:" + EventTypeMap.getEventTypeToEventMap().get(Integer.parseInt(resultEvent.getEventType()));
        StringBuilder mailContent = new StringBuilder();
        mailContent.append("objectdn:").append(resultEvent.getObjDN()).append("\n");
        mailContent.append("eventname:").append(EventTypeMap.getEventTypeToEventMap().get(Integer.parseInt(resultEvent.getEventType()))).append("\n");
        mailContent.append("level:").append(getEventLevelByEventType(resultEvent.getEventType())).append("\n");
        mailContent.append("detail:").append(resultEvent.getDetail()).append("\n");

        for (String receiver : receiverInfo) {
            MailSenderInfo mailInfo = getMailSenderInfo("zhang.pei162@zte.com.cn", receiver, mailTitle, mailContent.toString());
            sms.sendTextMail(mailInfo);//发送文体格式
        }
        //这个类主要来发送邮件
//        sms.sendHtmlMail(mailInfo);//发送html格式
        System.out.println("send mail success...");
    }

    private MailSenderInfo getMailSenderInfo(String fromMailAddress, String toMailAddress, String mailTitle, String mailContent) {
        MailSenderInfo mailInfo = new MailSenderInfo();
        mailInfo.setMailServerHost(EnvConstant.MAIL_SERVER_HOST);
        mailInfo.setMailServerPort(EnvConstant.MAIL_SERVER_PORT);
        mailInfo.setValidate(true);
        mailInfo.setUserName(EnvConstant.MAIL_SERVER_USER_NAME);   //自己的邮箱
        mailInfo.setPassword(EnvConstant.MAIL_SERVER_PASSWORD);//自己的邮箱密码，用于验证

        mailInfo.setFromAddress(fromMailAddress);  ///自己的邮箱
        mailInfo.setToAddress(toMailAddress);   ///对方的邮箱
        mailInfo.setSubject(mailTitle);
        mailInfo.setContent(mailContent);
        return mailInfo;

    }
}
