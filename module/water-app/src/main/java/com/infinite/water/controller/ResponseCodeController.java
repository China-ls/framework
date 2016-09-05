package com.infinite.water.controller;

import com.infinite.water.controller.resp.CodePagerResponse;
import com.infinite.water.controller.resp.CodeRestResponse;
import com.infinite.water.controller.resp.ResponseCode;
import com.infinite.water.core.controller.AbstractController;

/**
 * @author hx on 16-8-23.
 */
public class ResponseCodeController extends AbstractController {

    public CodeRestResponse makeRestResponse(ResponseCode code) {
        return new CodeRestResponse(code);
    }

    public CodeRestResponse makeRestResponse(ResponseCode code, Object data) {
        return new CodeRestResponse(code, data);
    }

    public CodePagerResponse makePagerResponse(ResponseCode code) {
        return new CodePagerResponse(code);
    }

    public CodePagerResponse makePagerResponse(ResponseCode code, Object data) {
        return new CodePagerResponse(code, data);
    }

    public CodePagerResponse makePagerResponse(ResponseCode code, Object data, long size, long total, long pages) {
        return new CodePagerResponse(code, data, size, total, pages);
    }

}
