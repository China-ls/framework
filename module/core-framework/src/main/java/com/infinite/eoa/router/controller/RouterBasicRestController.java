package com.infinite.eoa.router.controller;

import com.infinite.eoa.core.web.BasicRestController;
import com.infinite.eoa.core.web.entity.Response;
import com.infinite.eoa.router.entity.ResponseCode;

public class RouterBasicRestController extends BasicRestController {
    public Response makeResponse(ResponseCode code) {
        return super.makeResponse(code.code, code.message);
    }

    public Response makeResponse(ResponseCode code, Object data) {
        return super.makeResponse(code.code, code.message, data);
    }

}
