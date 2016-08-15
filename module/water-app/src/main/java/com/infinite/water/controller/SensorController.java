package com.infinite.water.controller;

import com.infinite.water.controller.resp.RestResponse;
import com.infinite.water.core.controller.AbstractController;
import com.infinite.water.service.VirtualSensorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hx on 16-7-29.
 */
@RequestMapping("/sensor")
@Controller("SensorController")
public class SensorController extends AbstractController {
    private static Logger log = LoggerFactory.getLogger(SensorController.class);

    @Autowired
    private VirtualSensorService virtualSensorService;

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse sensor(@PathVariable("id") String id) {
        RestResponse response = new RestResponse();
        try {
            response.setCode("0000");
            response.setMessage("success");
            response.setData(virtualSensorService.getVirtualSensor(id));
        } catch (Throwable e) {
            log.debug("read xdepartment.json error: {}", e.getCause());
            response.setCode("0001");
            response.setMessage("fail");
        }
        return response;
    }

}
