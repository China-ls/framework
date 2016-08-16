package com.infinite.framework.entity;

import com.infinite.framework.core.entity.AbstractEntity;

public class PersistentObject extends AbstractEntity {
    private String appkey;

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }
}
