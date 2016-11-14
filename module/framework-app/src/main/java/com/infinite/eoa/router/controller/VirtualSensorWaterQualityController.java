package com.infinite.eoa.router.controller;

import com.infinite.eoa.core.web.BasicRestController;
import com.infinite.eoa.core.web.entity.Response;
import com.infinite.eoa.entity.VirtualSensorWaterQuality;
import com.infinite.eoa.router.entity.ResponseCode;
import com.infinite.eoa.service.VirtualSensorWaterQualityService;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/sensor/waterquality")
@RestController("VirtualSensorWaterQualityController")
public class VirtualSensorWaterQualityController extends BasicRestController {
    private static Logger log = LoggerFactory.getLogger(VirtualSensorWaterQualityController.class);

    @Autowired
    private VirtualSensorWaterQualityService sensorWaterQualityService;

    @RequestMapping(value = "/add", method = {RequestMethod.PUT, RequestMethod.POST})
    @ResponseBody
    public Response addWaterQuality(
            @ModelAttribute VirtualSensorWaterQuality quality
    ) {
        Response response = null;
        try {
            response = makeResponse(
                    ResponseCode.SUCCESS,
                    sensorWaterQualityService.save(quality));
        } catch (Throwable e) {
            if (log.isErrorEnabled()) {
                log.error("error [{}]", quality, e);
            }
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
        }
        if (log.isDebugEnabled()) {
            log.debug("[entity: {}, response:{}]", quality, response);
        }
        return response;
    }

    @RequestMapping(value = "/{id}/page", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Response listPage(
            @PathVariable("id") String id,
            @ModelAttribute("page") String page,
            @ModelAttribute("size") String size
    ) {
        Response response = null;
        try {
            response = makeResponse(
                    ResponseCode.SUCCESS,
                    sensorWaterQualityService.listPager(
                            id, NumberUtils.toInt(page), NumberUtils.toInt(size)
                    ));
        } catch (Throwable e) {
            if (log.isErrorEnabled()) {
                log.error("error  [id: {},page: {}, size: {}]", id, page, size, e);
            }
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
        }
        if (log.isDebugEnabled()) {
            log.debug("[id: {},page: {}, size: {}, response:{}]", id, page, size, response);
        }
        return response;
    }

}
