package com.infinite.eoa.router.controller;

import com.infinite.eoa.core.util.TimeUtils;
import com.infinite.eoa.core.web.BasicRestController;
import com.infinite.eoa.core.web.entity.Response;
import com.infinite.eoa.router.entity.ResponseCode;
import com.infinite.eoa.service.VirtualSensorDataService;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.commons.lang.time.DateUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

@RequestMapping("/sensor/data")
@RestController("VirtualSensorDataController")
public class VirtualSensorDataController extends BasicRestController {
    private static Logger log = LoggerFactory.getLogger(VirtualSensorDataController.class);

    @Autowired
    private VirtualSensorDataService virtualSensorDataService;

    @RequestMapping(value = "/{id}/today", method = RequestMethod.GET)
    @ResponseBody
    public Response getTodayData(
            @PathVariable("id") String id,
            @ModelAttribute("comp_type") String comp_type
    ) {
        Response response = null;
        try {
            response = makeResponse(
                    ResponseCode.SUCCESS,
                    virtualSensorDataService.findBySensorIdAndTimeDistance(APPKEY, id,
                            comp_type,
                            TimeUtils.getTodayStartTime(), 0
                    ));
        } catch (Throwable e) {
            if (log.isErrorEnabled()) {
                log.error("get today data error [id : {},compy_type : {}]", id, comp_type, e);
            }
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
        }
        if (log.isDebugEnabled()) {
            log.debug("[id : {},compy_type : {}, resp : {}]", id, comp_type, response);
        }
        return response;
    }

    @RequestMapping(value = "/{id}/water", method = RequestMethod.GET)
    @ResponseBody
    public Response getWaterData(
            @PathVariable("id") String id
    ) {
        Response response = null;
        try {
            response = makeResponse(
                    ResponseCode.SUCCESS,
                    virtualSensorDataService.findWaterDataBySensorId(APPKEY, id)
            );
        } catch (Throwable e) {
            if (log.isErrorEnabled()) {
                log.error("get water data [id : {}]", id, e);
            }
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
        }
        if (log.isDebugEnabled()) {
            log.debug("[id : {}, resp : {}]", id, response);
        }
        return response;
    }

    @RequestMapping(value = "/{id}/electric", method = RequestMethod.GET)
    @ResponseBody
    public Response getElectricData(
            @PathVariable("id") String id
    ) {
        Response response = null;
        try {
            response = makeResponse(
                    ResponseCode.SUCCESS,
                    virtualSensorDataService.findElectricDataBySensorId(APPKEY, id)
            );
        } catch (Throwable e) {
            if (log.isErrorEnabled()) {
                log.error("get water data [id : {}]", id, e);
            }
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
        }
        if (log.isDebugEnabled()) {
            log.debug("[id : {}, resp : {}]", id, response);
        }
        return response;
    }

    @RequestMapping(value = "/{id}/degree", method = RequestMethod.GET)
    @ResponseBody
    public Response getDataDegree(
            @PathVariable("id") String id,
            @ModelAttribute("type") String typeText
    ) {
        Response response = null;
        try {
            int type = NumberUtils.toInt(typeText);
            response = makeResponse(
                    ResponseCode.SUCCESS,
                    virtualSensorDataService.findFieldDegreeByInterval(
                            APPKEY, id, "positive_total", NumberUtils.toInt(typeText))
            );
        } catch (Throwable e) {
            if (log.isErrorEnabled()) {
                log.error("get data degree error [id: {}]", id, e);
            }
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
        }
        if (log.isDebugEnabled()) {
            log.debug("[id: {}, resp: {}]", id, response);
        }
        return response;
    }

    @RequestMapping(value = "/{id}/month", method = RequestMethod.GET)
    @ResponseBody
    public Response getMonthHandlerTotal(@PathVariable("id") String id) {
        Response response = null;
        try {
            response = makeResponse(
                    ResponseCode.SUCCESS,
                    virtualSensorDataService.findFieldChangeByDays(
                            APPKEY, id, "positive_total", TimeUtils.getMonthDaysArray())
            );
        } catch (Throwable e) {
            if (log.isErrorEnabled()) {
                log.error("get month handler total error [id: {}]", id, e);
            }
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
        }
        if (log.isDebugEnabled()) {
            log.debug("[id: {}, resp: {}]", id, response);
        }
        return response;
    }

    @RequestMapping(value = "/{id}/image", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Response getImageData(
            @PathVariable("id") String sensorid,
            @ModelAttribute("start") String startText,
            @ModelAttribute("end") String endText
    ) {
        Response response = null;
        try {
//            log.debug("[id:{}, t:{}, s:{}, e:{}]", sensorid, type, start, end);
            String[] pattern = {"yyyy-MM-dd", "yyyy-MM-dd HH:mm:ss"};
            Date start = DateUtils.parseDate(startText, pattern);
            Date end = DateUtils.parseDate(endText, pattern);
            response = makeResponse(ResponseCode.SUCCESS,
                    virtualSensorDataService.getImageData(
                            APPKEY, sensorid, start.getTime(), end.getTime())
            );
        } catch (ParseException e) {
            if (log.isErrorEnabled()) {
                log.error("get image data error [id: {}, start:{}, end:{}}", sensorid, startText, endText, e);
            }
            response = makeResponse(ResponseCode.PARAM_EMPTY);
        } catch (Throwable e) {
            if (log.isErrorEnabled()) {
                log.error("get image data error [id: {}, start:{}, end:{}}", sensorid, startText, endText, e);
            }
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
        }
        if (log.isDebugEnabled()) {
            log.debug("[id: {}, start:{}, end:{}, resp: {}]", sensorid, startText, endText, response);
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
            response.setContentType("image/jpeg");
            Document document = virtualSensorDataService.findById(
                    APPKEY, id, Arrays.asList("image"));
            String jpegBase64 = null == document ? null : document.getString("image");
            if (null != jpegBase64) {
                byte[] jpegData = Base64.decodeBase64(jpegBase64.replace("data:image/jpeg;base64,", ""));
                OutputStream out = response.getOutputStream();
                out.write(jpegData);
                out.flush();
            }
        } catch (Throwable e) {
            if (log.isDebugEnabled()) {
                log.debug("error id : {}", id, e);
            }
        }
    }

}
