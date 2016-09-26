package com.infinite.eoa.entity;

import com.infinite.eoa.core.entity.AbstractEntity;

public class PersistentObject extends AbstractEntity {
    private String appkey;

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }
}
