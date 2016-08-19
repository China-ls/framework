package com.infinite.framework.router.entity;

import com.infinite.framework.core.entity.AbstractEntity;

/**
 * @author by hx on 16-7-6.
 */
public class LoginResponse extends AbstractEntity {
    private String username;
    private String appkey;
    private String password;
    private String token;

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "username='" + username + '\'' +
                ", appkey='" + appkey + '\'' +
                ", password='" + password + '\'' +
                ", token='" + token + '\'' +
                '}';
    }
}