package com.infinite.water.controller.resp;

import com.infinite.water.entity.BasicEntity;

import java.util.Arrays;

public class RestResponse extends BasicEntity {
    private String code;
    private String message;
    private Object data;
    private Object[] array;

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

    public Object[] getArray() {
        return array;
    }

    public void setArray(Object[] array) {
        this.array = array;
    }

    @Override
    public String toString() {
        return "RestResponse{" +
                "code='" + code + '\'' +
                ", message='" + message + '\'' +
                ", data=" + data +
                ", array=" + Arrays.toString(array) +
                '}';
    }
}
