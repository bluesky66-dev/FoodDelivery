package com.semo_prjects.fooddelivery.Database;

import android.net.Uri;

public class Accounts {
    private String mName;
    private String mNumber;
    private String mEmail;
    private String mPassword;
    private String mAccountType;
    private String mStatus;


    public Accounts() {
    }

    public Accounts(String name, String number, String email, String password, String accountType, String status) {
        mName = name;
        mNumber = number;
        mEmail = email;
        mPassword = password;
        mAccountType = accountType;
        mStatus = status;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        mNumber = number;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    public String getAccountType() {
        return mAccountType;
    }

    public void setAccountType(String accountType) {
        mAccountType = accountType;
    }

    public String getStatus() {
        return mStatus;
    }

    public void setStatus(String status) {
        mStatus = status;
    }
}

