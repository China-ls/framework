package com.infinite.eoa.core.web;

import com.infinite.eoa.core.web.entity.Response;
import com.infinite.eoa.router.entity.ResponseCode;

public class BasicRestController extends BasicController implements IRestController {
    public Response makeResponse(ResponseCode code) {
        return new Response(code.code, code.message);
    }

    public Response makeResponse(ResponseCode code, Object data) {
        return new Response(code.code, code.message, data);
    }

}
