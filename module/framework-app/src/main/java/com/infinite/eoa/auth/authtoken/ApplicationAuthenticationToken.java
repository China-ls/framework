package com.infinite.eoa.auth.authtoken;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

public class ApplicationAuthenticationToken implements HostAuthenticationToken, RememberMeAuthenticationToken {
    private String appkey;
    private boolean rememberMe = false;
    private String host;

    public ApplicationAuthenticationToken() {
    }

    public ApplicationAuthenticationToken(final String appkey) {
        this(appkey, false, null);
    }

    public ApplicationAuthenticationToken(final String appkey, final String host) {
        this(appkey, false, host);
    }

    public ApplicationAuthenticationToken(final String appkey, final boolean rememberMe) {
        this(appkey, rememberMe, null);
    }

    public ApplicationAuthenticationToken(final String appkey,
                                          final boolean rememberMe, final String host) {
        this.appkey = appkey;
        this.rememberMe = rememberMe;
        this.host = host;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public Object getPrincipal() {
        return getAppkey();
    }

    public Object getCredentials() {
        return getAppkey();
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public boolean isRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public void clear() {
        this.appkey = null;
        this.host = null;
        this.rememberMe = false;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getName());
        sb.append(" - ");
        sb.append(appkey);
        sb.append(", rememberMe=").append(rememberMe);
        if (host != null) {
            sb.append(" (").append(host).append(")");
        }
        return sb.toString();
    }
}