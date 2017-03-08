package com.zte.ums.smartsight.policy.domain.model;

import java.io.Serializable;

/**
 * Created by 10165228 on 2016/10/27.
 */
public class Receiver implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String email;
    private String phoneNumber;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
