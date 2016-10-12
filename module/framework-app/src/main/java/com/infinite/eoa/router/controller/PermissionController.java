package com.infinite.eoa.router.controller;

import com.infinite.eoa.core.web.BasicRestController;
import com.infinite.eoa.core.web.entity.Response;
import com.infinite.eoa.router.entity.ResponseCode;
import com.infinite.eoa.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/permission")
@RestController
public class PermissionController extends BasicRestController {
    private static Logger log = LoggerFactory.getLogger(PermissionController.class);

    @Autowired
    private PermissionService permissionService;

    @RequestMapping(value = "/list", method = {RequestMethod.GET})
    @ResponseBody
    public Response get() {
        Response response = null;
        try {
            response = makeResponse(ResponseCode.SUCCESS, permissionService.findAll());
        } catch (Exception e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("list permission.", e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("resp:{}", response);
        }
        return response;
    }

}
