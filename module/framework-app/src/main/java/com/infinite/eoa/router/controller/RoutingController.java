package com.infinite.eoa.router.controller;

import com.infinite.eoa.core.web.BasicRestController;
import com.infinite.eoa.core.web.entity.Response;
import com.infinite.eoa.router.entity.ResponseCode;
import com.infinite.eoa.service.VirtualSensorDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author hx on 16-7-6.
 * @since 1.0
 */
@RequestMapping(value = "/routing")
@RestController
public class RoutingController extends BasicRestController {
    private static Logger logger = LoggerFactory.getLogger(RoutingController.class);

    @Autowired
    private VirtualSensorDataService virtualSensorDataService;

    @RequestMapping(value = "/{id}/points", method = {RequestMethod.GET, RequestMethod.POST})
    public Response getRouterTodayPoints(@PathVariable("id") String id) {
        Response response = null;
        try {
            response = makeResponse(ResponseCode.SUCCESS, virtualSensorDataService.getEmployeeTodayPoints(id));
        } catch (Throwable e) {
            logger.debug("get router today points [id:{}] error.", id, e);
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
        }
        if (logger.isDebugEnabled()) {
            logger.debug("[id:{},resp:{}]", id, response);
        }
        return response;
    }

}