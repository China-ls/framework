package com.infinite.eoa.core.web.entity;

import com.infinite.eoa.core.entity.AbstractEntity;

/**
 * Created by hx handler 16-6-15.
 */
public class Response extends AbstractEntity {
    private String code;
    private String message;
    private Object data;

    public Response() {
        this(null, null, null);
    }

    public Response(String code) {
        this(code, null, null);
    }

    public Response(String code, String message) {
        this(code, message, null);
    }

    public Response(String code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
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

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Response{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
