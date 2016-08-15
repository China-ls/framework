package com.infinite.framework.router.controller;

import com.infinite.framework.core.web.BasicRestController;
import com.infinite.framework.entity.VirtualSensor;
import com.infinite.framework.service.VirtualSensorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author by hx on 16-7-4.
 */
@RequestMapping("/sensor")
@RestController("VirtualSensorController")
public class VirtualSensorController extends BasicRestController {
    private static Logger log = LoggerFactory.getLogger(VirtualSensorController.class);

    @Autowired
    private VirtualSensorService sensorService;

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    @ResponseBody
    public VirtualSensor create(VirtualSensor sensor) {
        return sensorService.createVirtualSensor(null, null);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public VirtualSensor get(@PathVariable("id") String id) {
        return sensorService.findById(id);
    }

}
