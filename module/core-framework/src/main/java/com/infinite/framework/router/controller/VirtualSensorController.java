package com.infinite.framework.router.controller;

import com.infinite.framework.core.web.BasicRestController;
import com.infinite.framework.entity.VirtualSensor;
import com.infinite.framework.service.VirtualSensorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

/**
 * @author by hx on 16-7-4.
 */
@RequestMapping("/sensor")
@RestController("VirtualSensorController")
public class VirtualSensorController extends BasicRestController {
    private static Logger log = LoggerFactory.getLogger(VirtualSensorController.class);

    private Random random = new Random();

    @Autowired
    private VirtualSensorService sensorService;

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    @ResponseBody
    public VirtualSensor create(@ModelAttribute("APPKEY") String appkey, @ModelAttribute VirtualSensor sensor) {
        return sensorService.createVirtualSensor(appkey, sensor);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public VirtualSensor get(@PathVariable("id") String id) {
        VirtualSensor sensor = sensorService.findById(id);
        if (null != sensor) {
            sensor.setInstant(2000 + random.nextInt(100));
            sensor.setPositive_total(4528.18);
        }
        return sensor;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public List<VirtualSensor> getAll() {
        List<VirtualSensor> sensors = sensorService.find();
        for (VirtualSensor sensor : sensors) {
            sensor.setInstant(2000 + random.nextInt(100));
            sensor.setPositive_total(4528.18);
        }
        return sensors;
    }

}
