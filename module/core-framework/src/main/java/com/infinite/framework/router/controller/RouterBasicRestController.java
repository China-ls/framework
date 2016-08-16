package com.infinite.framework.router.controller;

import com.infinite.framework.core.web.BasicRestController;
import com.infinite.framework.core.web.entity.Response;
import com.infinite.framework.router.entity.ResponseCode;

public class RouterBasicRestController extends BasicRestController {
    public Response makeResponse(ResponseCode code) {
        return super.makeResponse(code.code, code.message);
    }

    public Response makeResponse(ResponseCode code, Object data) {
        return super.makeResponse(code.code, code.message, data);
    }

}
