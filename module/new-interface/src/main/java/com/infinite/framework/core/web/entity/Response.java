package com.infinite.framework.core.web.entity;

import com.infinite.framework.core.entity.AbstractEntity;

/**
 * Created by hx handler 16-6-15.
 */
public class Response extends AbstractEntity {
    private String code;
    private String message;
    private Object obj;

    public Response() {
        this(null, null, null);
    }

    public Response(String code) {
        this(code, null, null);
    }

    public Response(String code, String message) {
        this(code, message, null);
    }

    public Response(String code, String message, Object obj) {
        this.code = code;
        this.message = message;
        this.obj = obj;
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

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }

    @Override
    public String toString() {
        return "AuthResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", obj=" + obj +
                '}';
    }
}
