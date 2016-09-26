package com.infinite.eoa.auth.client.entity;

/**
 * Created by hx on 16-7-4.
 */
public class AuthToken extends AuthResponse {
    private String token;
    private long expireTime;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getExpireTime() {
        return expireTime;
    }

    public void setExpireTime(long expireTime) {
        this.expireTime = expireTime;
    }

    @Override
    public String toString() {
        return "AuthToken{" +
                "token='" + token + '\'' +
                ", expireTime=" + expireTime +
                '}';
    }
}
