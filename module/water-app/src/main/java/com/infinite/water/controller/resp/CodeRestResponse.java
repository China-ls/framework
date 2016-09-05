package com.infinite.water.controller.resp;

import com.infinite.water.core.controller.response.RestResponse;

public class CodeRestResponse extends RestResponse {
    private Object data;

    public CodeRestResponse(ResponseCode code) {
        this(code, null);
    }

    public CodeRestResponse(ResponseCode code, Object data) {
        super(code.code, code.message);
        this.data = data;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
