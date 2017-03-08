package com.zte.ums.smartsight.policy.domain.model;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

/**
 * Created by 10183966 on 10/11/16.
 */


public class MailServerAuthenticator extends Authenticator {
    private String userName = null;
    private String password = null;

    public MailServerAuthenticator(String username, String password) {
        this.userName = username;
        this.password = password;
    }

    protected PasswordAuthentication getPasswordAuthentication() {
        return new PasswordAuthentication(userName, password);
    }

}
