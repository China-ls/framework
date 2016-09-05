package com.infinite.framework.entity;

import com.google.gson.annotations.SerializedName;

public class PersistentUser {
    private String id;
    private String name;
    private byte[] icon;
    private String username;
    private String password;
    private String pwdEncryLogic;
    private String pwdEncryKey;
    private String pwdEncrySolt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public byte[] getIcon() {
        return icon;
    }

    public void setIcon(byte[] icon) {
        this.icon = icon;
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

    public String getPwdEncryLogic() {
        return pwdEncryLogic;
    }

    public void setPwdEncryLogic(String pwdEncryLogic) {
        this.pwdEncryLogic = pwdEncryLogic;
    }

    public String getPwdEncryKey() {
        return pwdEncryKey;
    }

    public void setPwdEncryKey(String pwdEncryKey) {
        this.pwdEncryKey = pwdEncryKey;
    }

    public String getPwdEncrySolt() {
        return pwdEncrySolt;
    }

    public void setPwdEncrySolt(String pwdEncrySolt) {
        this.pwdEncrySolt = pwdEncrySolt;
    }
}
