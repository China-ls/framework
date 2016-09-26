package com.infinite.eoa.router.controller;

import com.infinite.eoa.core.web.BasicRestController;
import com.infinite.eoa.core.web.entity.Response;
import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.router.entity.ResponseCode;
import com.infinite.eoa.router.entity.SensorResponse;
import com.infinite.eoa.service.VirtualSensorDataService;
import com.infinite.eoa.service.VirtualSensorService;
import com.infinite.eoa.service.exception.ApplicationNotExsistException;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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

    @Autowired
    private VirtualSensorDataService virtualSensorDataService;

    @RequestMapping(value = "/", method = RequestMethod.PUT)
    @ResponseBody
    public VirtualSensor create(@RequestHeader("APPKEY") String appkey,
                                @ModelAttribute VirtualSensor sensor) {
        return sensorService.createVirtualSensor(appkey, sensor);
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Response get(@RequestHeader("APPKEY") String appkey,
                        @PathVariable("id") String id) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(appkey)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else {
                SensorResponse sensorResponse = new SensorResponse();
                sensorResponse.setSensor(sensorService.findById(appkey, id));
                sensorResponse.setData(virtualSensorDataService.findLatestBySensorId(appkey, id));
                response = makeResponse(ResponseCode.SUCCESS, sensorResponse);
            }
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
        } catch (Throwable e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error find [appkey:{}, id:{}]", appkey, id, e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[appkey:{}, id:{}, response:{}]", appkey, id, response);
        }
        return response;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Response getAll(@RequestHeader("APPKEY") String appkey) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(appkey)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else {
                ArrayList<SensorResponse> sensorResponseList = new ArrayList<SensorResponse>();
                List<VirtualSensor> sensors = sensorService.find();
                for (VirtualSensor sensor : sensors) {
                    SensorResponse sensorResponse = new SensorResponse();
                    sensorResponse.setSensor(sensorService.findById(appkey, sensor.getSensor_id()));
                    sensorResponse.setData(virtualSensorDataService.findLatestBySensorId(appkey, sensor.getSensor_id()));
                    sensorResponseList.add(sensorResponse);
                }
                response = makeResponse(ResponseCode.SUCCESS, sensorResponseList);
            }
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
        } catch (Throwable e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error find all [appkey:{}]", appkey, e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[appkey:{}, response:{}]", appkey, response);
        }
        return response;
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Response getByIds(@RequestHeader("APPKEY") String appkey,
                             @ModelAttribute("ids") String... idArray) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(appkey)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else if (null == idArray || idArray.length <= 0) {
                response = makeResponse(ResponseCode.PARAM_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS, sensorService.findByIdArray(appkey, idArray));
            }
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
        } catch (Throwable e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error find [appkey:{}, ids:{}, response:{}]", appkey, Arrays.toString(idArray), e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[appkey:{}, ids:{}, response:{}]", appkey, Arrays.toString(idArray), response);
        }
        return response;
    }

    @RequestMapping(value = "/fieldfilter", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Response getSensorByFieldFilter(@RequestHeader("APPKEY") String appkey,
                                           @ModelAttribute HashMap<String, String> filter) {
        Response response = null;
//        try {
//            if (StringUtils.isEmpty(appkey)) {
//                response = makeResponse(ResponseCode.APPKEY_EMPTY);
//            } else if (filter.isEmpty()) {
//                response = makeResponse(ResponseCode.PARAM_EMPTY);
//            } else {
//                response = makeResponse(ResponseCode.SUCCESS,
//                        sensorService.findByFilter(appkey, filter));
//            }
//        } catch (ApplicationNotExsistException e) {
//            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
//        } catch (Throwable e) {
//            response = makeResponse(ResponseCode.SYSTEM_ERROR);
//            if (log.isErrorEnabled()) {
//                log.error("error get sensor [appkey:{}, filter:{}].", appkey, filter, e);
//            }
//        }
//        if (log.isDebugEnabled()) {
//            log.debug("[appkey:{}, filter:{}, response:{}]", appkey, filter, response);
//        }
        return response;
    }

    @RequestMapping(value = "/filter", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Response getSensorByFilter(@RequestHeader("APPKEY") String appkey,
                                      @ModelAttribute("filter") String filter) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(appkey)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else if (StringUtils.isEmpty(filter)) {
                response = makeResponse(ResponseCode.PARAM_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS,
                        sensorService.findByFilter(appkey, filter));
            }
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
        } catch (Throwable e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error get sensor [appkey:{}, filter:{}].", appkey, filter, e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[appkey:{}, filter:{}, response:{}]", appkey, filter, response);
        }
        return response;
    }

    @RequestMapping(value = "/{id}/data/latest", method = RequestMethod.GET)
    @ResponseBody
    public Response getSensorLatestData(@RequestHeader("APPKEY") String appkey,
                                        @PathVariable("id") String id) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(appkey)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else if (StringUtils.isEmpty(id)) {
                response = makeResponse(ResponseCode.PARAM_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS,
                        virtualSensorDataService.findLatestBySensorId(appkey, id));
            }
        } catch (Throwable e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error get [id:{}] latest data.", id, e);
            }
        }
        return response;
    }

    @RequestMapping(value = "/{id}/data/between", method = RequestMethod.GET)
    @ResponseBody
    public Response getSensorTimeBetween(
            @RequestHeader("APPKEY") String appkey,
            @PathVariable("id") String id,
            @ModelAttribute("comp_type") String comp_type,
            @ModelAttribute("start") String start,
            @ModelAttribute("end") String end) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(appkey)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else if (StringUtils.isEmpty(start)) {
                response = makeResponse(ResponseCode.PARAM_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS,
                        virtualSensorDataService.findBySensorIdAndTimeDistance(appkey, id,
                                comp_type,
                                NumberUtils.toLong(start), NumberUtils.toLong(end)
                        ));
            }
        } catch (Throwable e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error get [id:{}, start:{}, end:{}] data.", id, start, end, e);
            }
        }
        return response;
    }

    @RequestMapping(value = "/{id}/data/filter", method = RequestMethod.GET)
    @ResponseBody
    public Response getSensorDataByFilter(@PathVariable("id") String id,
                                          @ModelAttribute("filter") String filter) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(filter)) {
                response = makeResponse(ResponseCode.PARAM_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS,
                        virtualSensorDataService.findBySensorIdAndFilter(id, filter));
            }
        } catch (Throwable e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error get [id:{}, filter:{}] data.", id, filter, e);
            }
        }
        return response;
    }

    @RequestMapping(value = "/{id}/data/fieldchange", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Response getDataFieldChangeByDays(
            @RequestHeader("APPKEY") String appkey,
            @PathVariable("id") String id,
            @ModelAttribute("field") String fieldname,
            @ModelAttribute("days") String... dayArray) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(appkey)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else if (null == dayArray || dayArray.length <= 0) {
                response = makeResponse(ResponseCode.PARAM_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS,
                        virtualSensorDataService.findFieldChangeByDays(appkey, id, fieldname, dayArray));
            }
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
        } catch (Throwable e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error find [appkey:{}, id:{}, field:{}, days:{}, response:{}]",
                        appkey, id, fieldname, Arrays.toString(dayArray), e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[appkey:{}, id:{}, field:{}, days:{}, response:{}]",
                    appkey, id, fieldname, Arrays.toString(dayArray), response);
        }
        return response;
    }

    @RequestMapping(value = "/{id}/data/projection", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Response getDataByFieldExsistAndProjection(
            @RequestHeader("APPKEY") String appkey,
            @PathVariable("id") String id,
            @ModelAttribute("offset") String offset,
            @ModelAttribute("limit") String limit,
            @ModelAttribute("ascsort") ArrayList<String> ascSort,
            @ModelAttribute("dscsort") ArrayList<String> dscSort,
            @ModelAttribute("exist") ArrayList<String> exsistArray,
            @ModelAttribute("field") ArrayList<String> fieldArray) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(appkey)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else if (null == fieldArray || fieldArray.size() <= 0) {
                response = makeResponse(ResponseCode.PARAM_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS,
                        virtualSensorDataService.findByFieldExsistAndProjection(
                                appkey, id, NumberUtils.toInt(offset),
                                NumberUtils.toInt(limit), exsistArray,
                                fieldArray, ascSort, dscSort));
            }
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
        } catch (Throwable e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error find [appkey:{}, id:{}, offset:{}, limit:{}, exist: {}, field:{}, response:{}]",
                        appkey, id, offset, limit, exsistArray,
                        fieldArray, response, e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[appkey:{}, id:{}, offset:{}, limit:{}, exist: {}, field:{}, response:{}]",
                    appkey, id, offset, limit, exsistArray,
                    fieldArray, response);
        }
        return response;
    }

    @RequestMapping(value = "/data", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Response getSensorData(
            @RequestHeader("APPKEY") String appkey,
            @ModelAttribute("id") String id,
            @ModelAttribute("field") ArrayList<String> fieldArray
    ) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(appkey)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else if (StringUtils.isEmpty(id)) {
                response = makeResponse(ResponseCode.PARAM_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS,
                        virtualSensorDataService.findById(
                                appkey, id, fieldArray));
            }
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
        } catch (Throwable e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error find [appkey:{}, id:{}, response:{}]",
                        appkey, id, response, e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[appkey:{}, id:{}, response:{}]",
                    appkey, id, response);
        }
        return response;
    }

}
