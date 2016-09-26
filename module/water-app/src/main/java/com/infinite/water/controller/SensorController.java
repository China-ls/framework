package com.infinite.water.controller;

import com.infinite.water.controller.resp.ResponseCode;
import com.infinite.water.core.controller.response.RestResponse;
import com.infinite.water.core.util.TimeUtils;
import com.infinite.water.service.VirtualSensorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;

/**
 * @author hx on 16-7-29.
 */
@RequestMapping("/sensor")
@Controller("SensorController")
public class SensorController extends ResponseCodeController {
    private static Logger log = LoggerFactory.getLogger(SensorController.class);

    @Autowired
    private VirtualSensorService virtualSensorService;

    @RequestMapping(value = "/{id}/data/latest", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse sensorData(@PathVariable("id") String id) {
        RestResponse response = null;
        try {
            response = makeRestResponse(
                    ResponseCode.SUCCESS, virtualSensorService.getVirtualSensorLatestData(id)
            );
        } catch (Throwable e) {
            if (log.isDebugEnabled()) {
                log.debug("read xdepartment.json error: {}", e.getCause());
            }
            response = makeRestResponse(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse sensor(@PathVariable("id") String id) {
        RestResponse response = null;
        try {
            response = makeRestResponse(
                    ResponseCode.SUCCESS, virtualSensorService.getVirtualSensor(id)
            );
        } catch (Throwable e) {
            if (log.isDebugEnabled()) {
                log.debug("read xdepartment.json error: {}", e.getCause());
            }
            response = makeRestResponse(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse getAllSensor() {
        RestResponse response = null;
        try {
            response = makeRestResponse(ResponseCode.SUCCESS,
                    virtualSensorService.getAllVirtualSensor());
        } catch (Throwable e) {
            if (log.isDebugEnabled()) {
                log.debug("read xdepartment.json error: {}", e.getCause());
            }
            response = makeRestResponse(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @RequestMapping(value = "/{id}/data/today", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse getTodayData(
            @PathVariable("id") String id,
            @ModelAttribute("comp_type") String comp_type
    ) {
        RestResponse response = null;
        try {
            response = makeRestResponse(
                    ResponseCode.SUCCESS,
                    virtualSensorService.getVirtualSensorDataBetween(
                            id, comp_type, TimeUtils.getTodayStartTime(), 0)
            );
        } catch (Throwable e) {
            if (log.isDebugEnabled()) {
                log.debug("error id : {}", id, e);
            }
            response = makeRestResponse(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @RequestMapping(value = "/{id}/data/month", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse getMonthHandlerTotal(
            @PathVariable("id") String id
    ) {
        RestResponse response = null;
        try {
            response = makeRestResponse(
                    ResponseCode.SUCCESS,
                    virtualSensorService.getVirtualSensorMonthTotalData(id)
            );
        } catch (Throwable e) {
            if (log.isDebugEnabled()) {
                log.debug("error id : {}", id, e);
            }
            response = makeRestResponse(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @RequestMapping(value = "/{id}/data/image", method = RequestMethod.GET)
    @ResponseBody
    public RestResponse getTop20Image(
            @PathVariable("id") String id
    ) {
        RestResponse response = null;
        try {
            response = makeRestResponse(
                    ResponseCode.SUCCESS,
                    virtualSensorService.getTop20Image(id)
            );
        } catch (Throwable e) {
            if (log.isDebugEnabled()) {
                log.debug("error id : {}", id, e);
            }
            response = makeRestResponse(ResponseCode.SYSTEM_ERROR);
        }
        return response;
    }

    @RequestMapping(value = "/image/{id}", method = RequestMethod.GET)
    @ResponseBody
    public void getImageById(
            @PathVariable("id") String id,
            HttpServletResponse response
    ) {
        try {
            byte[] jpegData = virtualSensorService.getSensorImage(id);
            response.setContentType("image/jpeg");
            OutputStream out = response.getOutputStream();
            out.write(jpegData);
            out.flush();
        } catch (Throwable e) {
            if (log.isDebugEnabled()) {
                log.debug("error id : {}", id, e);
            }
        }
    }

}
