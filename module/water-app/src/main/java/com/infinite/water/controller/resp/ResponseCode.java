package com.infinite.water.controller.resp;

/**
 * @author by hx on 16-8-23.
 */
public enum ResponseCode {

    SUCCESS("0", "SUCCESS"),
    SYSTEM_ERROR("1", "SYSTEM ERROR"),
    DATA_ERROR("2", "DATA ERROR"),
    ;

    public final String code;
    public final String message;

    private ResponseCode(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
