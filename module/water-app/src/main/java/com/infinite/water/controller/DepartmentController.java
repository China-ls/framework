package com.infinite.water.controller;

import com.infinite.water.controller.resp.ResponseCode;
import com.infinite.water.core.controller.response.RestResponse;
import com.infinite.water.service.DepartmentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hx on 16-7-29.
 */
@RequestMapping("/dpt")
@Controller("DepartmentController")
public class DepartmentController extends ResponseCodeController {
    private static Logger log = LoggerFactory.getLogger(DepartmentController.class);

    @Autowired
    private DepartmentService departmentService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse department() {
        RestResponse response = null;
        try {
            response = makeRestResponse(ResponseCode.SUCCESS, departmentService.getDepartment());
        } catch (Throwable e) {
            log.debug("read xdepartment.json error: {}", e.getCause());
            response = makeRestResponse(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @RequestMapping(value = "/route", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse departmentRoute() {
        RestResponse response = null;
        try {
            response = makeRestResponse(ResponseCode.SUCCESS, departmentService.getDepartmentRoute());
        } catch (Throwable e) {
            log.debug("read xdepartment.json error: {}", e.getCause());
            response = makeRestResponse(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

}
