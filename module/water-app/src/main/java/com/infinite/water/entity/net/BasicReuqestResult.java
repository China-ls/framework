package com.infinite.water.entity.net;

import com.infinite.water.entity.BasicEntity;

/**
 * @author hx on 16-8-23.
 */
public class BasicReuqestResult extends BasicEntity {
    private String code;
    private String message;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
