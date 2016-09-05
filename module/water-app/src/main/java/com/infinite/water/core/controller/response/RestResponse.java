package com.infinite.water.core.controller.response;

import com.infinite.water.entity.BasicEntity;

public class RestResponse extends BasicEntity {
    private String code;
    private String message;

    public RestResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

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
