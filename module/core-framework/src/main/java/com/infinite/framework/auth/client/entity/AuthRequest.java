package com.infinite.framework.auth.client.entity;

import com.infinite.framework.core.entity.AbstractEntity;

/**
 * Created by hx on 16-7-4.
 */
public class AuthRequest extends AbstractEntity {
    private String account;
    private String password;
    private String applicationKey;
    private String token;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getApplicationKey() {
        return applicationKey;
    }

    public void setApplicationKey(String applicationKey) {
        this.applicationKey = applicationKey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
