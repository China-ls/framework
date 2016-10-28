package com.infinite.eoa.router.controller;

import com.infinite.eoa.core.web.BasicRestController;
import com.infinite.eoa.core.web.entity.Response;
import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.router.entity.ResponseCode;
import com.infinite.eoa.router.entity.SensorModel;
import com.infinite.eoa.router.entity.SensorResponse;
import com.infinite.eoa.service.ComponentWorkTimeCencusService;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
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
    @Autowired
    private ComponentWorkTimeCencusService componentWorkTimeCencusService;

    @RequestMapping(value = "/add", method = {RequestMethod.PUT, RequestMethod.POST})
    @ResponseBody
    public Response create(@ModelAttribute SensorModel sensorModel) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(APPKEY)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else {
                VirtualSensor sensor = sensorModel.convert();
                log.debug("{}", sensor);
                response = makeResponse(ResponseCode.SUCCESS,
                        sensorService.createVirtualSensor(APPKEY, sensor)
                );
            }
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
        } catch (Throwable e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error add [{}]", sensorModel, e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[sensor:{}, response:{}]", sensorModel, response);
        }
        return response;
    }

    @RequestMapping(value = "/{id}", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Response get(@PathVariable("id") String id) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(APPKEY)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else {
                SensorResponse sensorResponse = new SensorResponse();
                VirtualSensor sensor = sensorService.findById(APPKEY, id);
                sensorResponse.setSensor(sensor);
                sensorResponse.classifyComponents();
                sensorResponse.setData(virtualSensorDataService.findLatestBySensorId(APPKEY, id));
                sensorResponse.setCompWorkTimeCencus(componentWorkTimeCencusService.getDayAndMonthBySensorId(id));
                response = makeResponse(ResponseCode.SUCCESS, sensorResponse);
            }
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
        } catch (Throwable e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error find [appkey:{}, id:{}]", APPKEY, id, e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[appkey:{}, id:{}, response:{}]", APPKEY, id, response);
        }
        return response;
    }

    @RequestMapping(value = "/page", method = RequestMethod.GET)
    @ResponseBody
    public Response listPage(
            @ModelAttribute("page") String page,
            @ModelAttribute("size") String size
    ) {
        Response response = null;
        try {
            response = makeResponse(ResponseCode.SUCCESS,
                    sensorService.listPager(NumberUtils.toInt(page), NumberUtils.toInt(size))
            );
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
        } catch (Throwable e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error(" [page:{}, size:{}]", page, size, e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[page:{}, size:{}, response:{}]", page, size, response);
        }
        return response;
    }

    @RequestMapping(value = "/all", method = RequestMethod.GET)
    @ResponseBody
    public Response getAll() {
        Response response = null;
        try {
            if (StringUtils.isEmpty(APPKEY)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else {
                ArrayList<SensorResponse> sensorResponseList = new ArrayList<SensorResponse>();
                List<VirtualSensor> sensors = sensorService.find();
                for (VirtualSensor sensor : sensors) {
                    SensorResponse sensorResponse = new SensorResponse();
                    sensorResponse.setSensor(sensorService.findById(APPKEY, sensor.getSensor_id()));
                    sensorResponse.setData(virtualSensorDataService.findLatestBySensorId(APPKEY, sensor.getSensor_id()));
                    sensorResponseList.add(sensorResponse);
                }
                response = makeResponse(ResponseCode.SUCCESS, sensorResponseList);
            }
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
        } catch (Throwable e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error find all [appkey:{}]", APPKEY, e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[appkey:{}, response:{}]", APPKEY, response);
        }
        return response;
    }

    @RequestMapping(value = "/", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Response getByIds(@ModelAttribute("ids") String... idArray) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(APPKEY)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else if (null == idArray || idArray.length <= 0) {
                response = makeResponse(ResponseCode.PARAM_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS, sensorService.findByIdArray(APPKEY, idArray));
            }
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
        } catch (Throwable e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error find [APPKEY:{}, ids:{}, response:{}]", APPKEY, Arrays.toString(idArray), e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[APPKEY:{}, ids:{}, response:{}]", APPKEY, Arrays.toString(idArray), response);
        }
        return response;
    }

    @RequestMapping(value = "/filter", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Response getSensorByFilter(
            @ModelAttribute("filter") String filter) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(APPKEY)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else if (StringUtils.isEmpty(filter)) {
                response = makeResponse(ResponseCode.PARAM_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS,
                        sensorService.findByFilter(APPKEY, filter));
            }
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
        } catch (Throwable e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error get sensor [APPKEY:{}, filter:{}].", APPKEY, filter, e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[APPKEY:{}, filter:{}, response:{}]", APPKEY, filter, response);
        }
        return response;
    }

    @RequestMapping(value = "/{id}/data/latest", method = RequestMethod.GET)
    @ResponseBody
    public Response getSensorLatestData(
            @PathVariable("id") String id) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(APPKEY)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else if (StringUtils.isEmpty(id)) {
                response = makeResponse(ResponseCode.PARAM_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS,
                        virtualSensorDataService.findLatestBySensorId(APPKEY, id));
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
            @PathVariable("id") String id,
            @ModelAttribute("comp_type") String comp_type,
            @ModelAttribute("start") String start,
            @ModelAttribute("end") String end) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(APPKEY)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else if (StringUtils.isEmpty(start)) {
                response = makeResponse(ResponseCode.PARAM_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS,
                        virtualSensorDataService.findBySensorIdAndTimeDistance(APPKEY, id,
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
            @PathVariable("id") String id,
            @ModelAttribute("field") String fieldname,
            @ModelAttribute("days") String... dayArray) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(APPKEY)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else if (null == dayArray || dayArray.length <= 0) {
                response = makeResponse(ResponseCode.PARAM_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS,
                        virtualSensorDataService.findFieldChangeByDays(APPKEY, id, fieldname, dayArray));
            }
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
        } catch (Throwable e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error find [APPKEY:{}, id:{}, field:{}, days:{}, response:{}]",
                        APPKEY, id, fieldname, Arrays.toString(dayArray), e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[APPKEY:{}, id:{}, field:{}, days:{}, response:{}]",
                    APPKEY, id, fieldname, Arrays.toString(dayArray), response);
        }
        return response;
    }

    @RequestMapping(value = "/{id}/data/projection", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Response getDataByFieldExsistAndProjection(
            @PathVariable("id") String id,
            @ModelAttribute("offset") String offset,
            @ModelAttribute("limit") String limit,
            @ModelAttribute("ascsort") ArrayList<String> ascSort,
            @ModelAttribute("dscsort") ArrayList<String> dscSort,
            @ModelAttribute("exist") ArrayList<String> exsistArray,
            @ModelAttribute("field") ArrayList<String> fieldArray) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(APPKEY)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else if (null == fieldArray || fieldArray.size() <= 0) {
                response = makeResponse(ResponseCode.PARAM_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS,
                        virtualSensorDataService.findByFieldExsistAndProjection(
                                APPKEY, id, NumberUtils.toInt(offset),
                                NumberUtils.toInt(limit), exsistArray,
                                fieldArray, ascSort, dscSort));
            }
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
        } catch (Throwable e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error find [APPKEY:{}, id:{}, offset:{}, limit:{}, exist: {}, field:{}, response:{}]",
                        APPKEY, id, offset, limit, exsistArray,
                        fieldArray, response, e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[APPKEY:{}, id:{}, offset:{}, limit:{}, exist: {}, field:{}, response:{}]",
                    APPKEY, id, offset, limit, exsistArray,
                    fieldArray, response);
        }
        return response;
    }

    @RequestMapping(value = "/data", method = {RequestMethod.GET, RequestMethod.POST})
    @ResponseBody
    public Response getSensorData(
            @ModelAttribute("id") String id,
            @ModelAttribute("field") ArrayList<String> fieldArray
    ) {
        Response response = null;
        try {
            if (StringUtils.isEmpty(APPKEY)) {
                response = makeResponse(ResponseCode.APPKEY_EMPTY);
            } else if (StringUtils.isEmpty(id)) {
                response = makeResponse(ResponseCode.PARAM_EMPTY);
            } else {
                response = makeResponse(ResponseCode.SUCCESS,
                        virtualSensorDataService.findById(
                                APPKEY, id, fieldArray));
            }
        } catch (ApplicationNotExsistException e) {
            response = makeResponse(ResponseCode.APPKEY_FORBIDON);
        } catch (Throwable e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (log.isErrorEnabled()) {
                log.error("error find [APPKEY:{}, id:{}, response:{}]",
                        APPKEY, id, response, e);
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("[APPKEY:{}, id:{}, response:{}]",
                    APPKEY, id, response);
        }
        return response;
    }

}
