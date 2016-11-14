package com.infinite.eoa.router.controller;

import com.infinite.eoa.core.util.TimeUtils;
import com.infinite.eoa.core.web.BasicRestController;
import com.infinite.eoa.core.web.entity.Response;
import com.infinite.eoa.entity.Component;
import com.infinite.eoa.entity.EntityConst;
import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.entity.cencus.ComponentDayWorkCencus;
import com.infinite.eoa.entity.cencus.SensorDayElectricUseageCencus;
import com.infinite.eoa.entity.cencus.SensorDayHandlerWaterCencus;
import com.infinite.eoa.entity.cencus.SensorDayHighWaterLevelCountCencus;
import com.infinite.eoa.entity.cencus.SensorDayMolErrorCountCencus;
import com.infinite.eoa.router.entity.ResponseCode;
import com.infinite.eoa.router.entity.SensorResponse;
import com.infinite.eoa.service.CencusEntityService;
import com.infinite.eoa.service.CencusService;
import com.infinite.eoa.service.VirtualSensorDataService;
import com.infinite.eoa.service.VirtualSensorService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.bson.Document;
import org.joda.time.DateTime;
import org.joda.time.Days;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

@RequestMapping("/cencus")
@RestController
public class CencusController extends BasicRestController {
    private static Logger LOG = LoggerFactory.getLogger(CencusController.class);

    @Autowired
    private CencusService cencusService;
    @Autowired
    private VirtualSensorService virtualSensorService;
    @Autowired
    private CencusEntityService cencusEntityService;
    @Autowired
    private VirtualSensorDataService virtualSensorDataService;

    @RequestMapping(value = "/device/type", method = RequestMethod.GET)
    @ResponseBody
    public Response devicetype() {
        Response response = null;
        try {
            response = makeResponse(ResponseCode.SUCCESS,
                    cencusService.deviceTypeDistribution()
            );
        } catch (Exception e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (LOG.isErrorEnabled()) {
                LOG.error(e.getMessage());
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("resp:{}", response);
        }
        return response;
    }

    @RequestMapping(value = "/device/online", method = RequestMethod.GET)
    @ResponseBody
    public Response deviceonlie() {
        Response response = null;
        try {
            response = makeResponse(ResponseCode.SUCCESS,
                    cencusService.deviceOnlineDistribution()
            );
        } catch (Exception e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (LOG.isErrorEnabled()) {
                LOG.error(e.getMessage());
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("resp:{}", response);
        }
        return response;
    }

    @RequestMapping(value = "/device/filter", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public Response deviceFilter(
            @ModelAttribute("station_type") String station_type,
            @ModelAttribute("online") String online,
            @ModelAttribute("ability") String day_deal_water_ability
    ) {
        Response response = null;
        try {
            response = makeResponse(ResponseCode.SUCCESS,
                    virtualSensorService.findByStation_typeAndOnlineAndAbility(
                            station_type, NumberUtils.toInt(online, -1), day_deal_water_ability
                    )
            );
        } catch (Exception e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (LOG.isErrorEnabled()) {
                LOG.error(e.getMessage());
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("resp:{}", response);
        }
        return response;
    }

    @RequestMapping(value = "/device/filter/export", method = RequestMethod.GET)
    public void deviceFilterExport(
            @ModelAttribute("station_type") String station_type,
            @ModelAttribute("online") String online,
            @ModelAttribute("ability") String day_deal_water_ability,
            HttpServletResponse response
    ) {
        try {
            List<VirtualSensor> virtualSensors = virtualSensorService.findByStation_typeAndOnlineAndAbility(
                    station_type, NumberUtils.toInt(online, -1), day_deal_water_ability
            );
            ArrayList<Map<String, Object>> excelDataMapList = new ArrayList<Map<String, Object>>();
            for (VirtualSensor vs : virtualSensors) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("name", vs.getName());
                map.put("departmentName", vs.getDepartmentName());
                map.put("station_type", vs.getStation_type());
                map.put("ability", vs.getDay_deal_water_ability());
                map.put("setup_date", vs.getSetup_date());
                int onlineInt = vs.getOnline();
                String onlineText = "";
                if (onlineInt == EntityConst.SensorOnline.YES) {
                    onlineText = "在线";
                } else if (onlineInt == EntityConst.SensorOnline.NO) {
                    onlineText = "离线";
                } else if (onlineInt == EntityConst.SensorOnline.IDLE) {
                    onlineText = "发呆";
                } else {
                    onlineText = "未知";
                }

                map.put("online", onlineText);
                map.put("admin", vs.getAdmin());
                map.put("contact", vs.getContact());
                map.put("longitude", vs.getLongitude());
                map.put("latitude", vs.getLatitude());
                map.put("address", vs.getAddress());
                excelDataMapList.add(map);
            }
            exportExcel("设备统计", "设备数据", excelDataMapList,
                    new String[]{"站点名称", "所属机构", "站点类型", "日处水能力(吨)", "投产时间", "站点状态", "站点管理员", "联系电话", "经度", "纬度", "地址"},
                    new String[]{"name", "departmentName", "station_type", "ability", "setup_date", "online", "admin", "contact", "longitude", "latitude", "address"},
                    response);
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error(e.getMessage());
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("resp:{}", response);
        }
    }

    @RequestMapping(value = "/flow/filter", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public Response deviceFilterFlow(
            @ModelAttribute("station_type") String station_type,
            @ModelAttribute("start") String start,
            @ModelAttribute("end") String end
    ) {
        Response response = null;
        try {
            List<VirtualSensor> sensors = virtualSensorService.findByStation_typeAndOnlineAndAbility(station_type, -1, null);
            ArrayList<Document> documents = null;
            if (null != sensors && sensors.size() > 0) {
                documents = new ArrayList<Document>();
                HashMap<String, Double> positiveTotal = cencusService.cencusPositiveTotalByStation_type(
                        station_type, NumberUtils.toLong(start), NumberUtils.toLong(end)
                );
                HashMap<String, Double> power = cencusService.cencusPowerByStation_type(
                        station_type, NumberUtils.toLong(start), NumberUtils.toLong(end)
                );
                for (VirtualSensor vs : sensors) {
                    Document document = new Document();
                    document.put("device", vs);
                    document.put("positiveTotal", positiveTotal.get(vs.getSensor_id()));
                    document.put("power", power.get(vs.getSensor_id()));
                    documents.add(document);
                }
            }
            response = makeResponse(ResponseCode.SUCCESS, documents);
        } catch (Exception e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (LOG.isErrorEnabled()) {
                LOG.error("station_type:{}, start:{}, end:{}, error:{}", station_type, start, end, e.getMessage());
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("station_type:{}, start:{}, end:{}, resp:{}", station_type, start, end, response);
        }
        return response;
    }

    @RequestMapping(value = "/flow/filter/export", method = RequestMethod.GET)
    public void deviceFilterFlowExport(
            @ModelAttribute("station_type") String station_type,
            @ModelAttribute("start") String start,
            @ModelAttribute("end") String end,
            HttpServletResponse response
    ) {
        try {
            List<VirtualSensor> sensors = virtualSensorService.findByStation_typeAndOnlineAndAbility(station_type, -1, null);
            ArrayList<Map<String, Object>> excelDataMapList = new ArrayList<Map<String, Object>>();
            if (null == sensors || sensors.size() <= 0) {
                return;
            }
            HashMap<String, Double> positiveTotal = cencusService.cencusPositiveTotalByStation_type(
                    station_type, NumberUtils.toLong(start), NumberUtils.toLong(end)
            );
            HashMap<String, Double> power = cencusService.cencusPowerByStation_type(
                    station_type, NumberUtils.toLong(start), NumberUtils.toLong(end)
            );
            for (VirtualSensor vs : sensors) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("name", vs.getName());
                map.put("departmentName", vs.getDepartmentName());
                map.put("station_type", vs.getStation_type());
                map.put("ability", vs.getDay_deal_water_ability());
                map.put("setup_date", vs.getSetup_date());
                int onlineInt = vs.getOnline();
                String onlineText = "";
                if (onlineInt == EntityConst.SensorOnline.YES) {
                    onlineText = "在线";
                } else if (onlineInt == EntityConst.SensorOnline.NO) {
                    onlineText = "离线";
                } else if (onlineInt == EntityConst.SensorOnline.IDLE) {
                    onlineText = "发呆";
                } else {
                    onlineText = "未知";
                }

                map.put("online", onlineText);
                map.put("admin", vs.getAdmin());
                map.put("contact", vs.getContact());
                map.put("longitude", vs.getLongitude());
                map.put("latitude", vs.getLatitude());
                map.put("address", vs.getAddress());
                map.put("positiveTotal", positiveTotal.get(vs.getSensor_id()));
                map.put("power", power.get(vs.getSensor_id()));
                excelDataMapList.add(map);
            }

            exportExcel("设备水电统计", "水电统计", excelDataMapList,
                    new String[]{"站点名称", "所属机构", "站点类型", "日处水能力(吨)", "投产时间", "站点状态", "站点管理员", "联系电话", "经度", "纬度", "地址", "总处水量(吨)", "总用电量(度)"},
                    new String[]{"name", "departmentName", "station_type", "ability", "setup_date", "online", "admin", "contact", "longitude", "latitude", "address", "positiveTotal", "power"},
                    response);
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error(e.getMessage());
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("resp:{}", response);
        }
    }

    Random random = new Random();

    @RequestMapping(value = "/routing/filter", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public Response deviceFilterRouting(
            @ModelAttribute("station_type") String station_type,
            @ModelAttribute("start") String start,
            @ModelAttribute("end") String end
    ) {
        Response response = null;
        try {
            List<VirtualSensor> sensors = virtualSensorService.findByStation_typeAndOnlineAndAbility(station_type, -1, null);
            DateTime startDt = new DateTime(NumberUtils.toLong(start));
            DateTime endDt = new DateTime(NumberUtils.toLong(end));

            ArrayList<Document> documents = null;
            if (null != sensors && sensors.size() > 0) {
                documents = new ArrayList<Document>();
                for (VirtualSensor vs : sensors) {
                    Document document = new Document();
                    document.put("device", vs);
                    document.put("count", random.nextInt(Days.daysBetween(startDt, endDt).getDays()));
                    documents.add(document);
                }
            }
            response = makeResponse(ResponseCode.SUCCESS, documents);
        } catch (Exception e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (LOG.isErrorEnabled()) {
                LOG.error("station_type:{}, start:{}, end:{}, error:{}", station_type, start, end, e.getMessage());
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("station_type:{}, start:{}, end:{}, resp:{}", station_type, start, end, response);
        }
        return response;
    }

    @RequestMapping(value = "/routing/filter/export", method = RequestMethod.GET)
    public void deviceFilterRoutingExport(
            @ModelAttribute("station_type") String station_type,
            @ModelAttribute("start") String start,
            @ModelAttribute("end") String end,
            HttpServletResponse response
    ) {
        try {
            List<VirtualSensor> sensors = virtualSensorService.findByStation_typeAndOnlineAndAbility(station_type, -1, null);
            ArrayList<Map<String, Object>> excelDataMapList = new ArrayList<Map<String, Object>>();
            if (null == sensors || sensors.size() <= 0) {
                return;
            }
            DateTime startDt = new DateTime(NumberUtils.toLong(start));
            DateTime endDt = new DateTime(NumberUtils.toLong(end));
            for (VirtualSensor vs : sensors) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("name", vs.getName());
                map.put("departmentName", vs.getDepartmentName());
                map.put("station_type", vs.getStation_type());
                map.put("ability", vs.getDay_deal_water_ability());
                map.put("setup_date", vs.getSetup_date());
                int onlineInt = vs.getOnline();
                String onlineText = "";
                if (onlineInt == EntityConst.SensorOnline.YES) {
                    onlineText = "在线";
                } else if (onlineInt == EntityConst.SensorOnline.NO) {
                    onlineText = "离线";
                } else if (onlineInt == EntityConst.SensorOnline.IDLE) {
                    onlineText = "发呆";
                } else {
                    onlineText = "未知";
                }

                map.put("online", onlineText);
                map.put("admin", vs.getAdmin());
                map.put("contact", vs.getContact());
                map.put("longitude", vs.getLongitude());
                map.put("latitude", vs.getLatitude());
                map.put("address", vs.getAddress());
                map.put("count", random.nextInt(Days.daysBetween(startDt, endDt).getDays()));
                excelDataMapList.add(map);
            }

            exportExcel("设备" + startDt.toString(TimeUtils.Pattern_Date) + "至" + endDt.toString(TimeUtils.Pattern_Date) + "巡检统计", "巡检统计", excelDataMapList,
                    new String[]{"站点名称", "所属机构", "站点类型", "日处水能力(吨)", "投产时间", "站点状态", "站点管理员", "联系电话", "经度", "纬度", "地址", "巡检次数"},
                    new String[]{"name", "departmentName", "station_type", "ability", "setup_date", "online", "admin", "contact", "longitude", "latitude", "address", "count"},
                    response);
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error(e.getMessage());
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("resp:{}", response);
        }
    }

    @RequestMapping(value = "/qos/filter", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public Response deviceFilterQos(
            @ModelAttribute("station_type") String station_type,
            @ModelAttribute("online") String online
    ) {
        Response response = null;
        try {
            List<VirtualSensor> sensors = virtualSensorService.findByStation_typeAndOnlineAndAbility(station_type, NumberUtils.toInt(online, -1), null);

            ArrayList<SensorResponse> documents = null;
            if (null != sensors && sensors.size() > 0) {
                documents = new ArrayList<SensorResponse>();
                for (VirtualSensor vs : sensors) {
                    SensorResponse sensorResponse = new SensorResponse();
                    sensorResponse.setSensor(vs);
                    sensorResponse.setData(virtualSensorDataService.findLatestBySensorId(APPKEY, vs.getSensor_id()));
                    documents.add(sensorResponse);
                }
            }
            response = makeResponse(ResponseCode.SUCCESS, documents);
        } catch (Exception e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (LOG.isErrorEnabled()) {
                LOG.error("station_type:{}, online:{}, error:{}", station_type, online, e.getMessage());
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("station_type:{}, online:{}, resp:{}", station_type, online, response);
        }
        return response;
    }

    @RequestMapping(value = "/qos/filter/export", method = RequestMethod.GET)
    public void deviceFilterQosExport(
            @ModelAttribute("station_type") String station_type,
            @ModelAttribute("start") String start,
            @ModelAttribute("end") String end,
            HttpServletResponse response
    ) {
        try {
            List<VirtualSensor> sensors = virtualSensorService.findByStation_typeAndOnlineAndAbility(station_type, -1, null);
            ArrayList<Map<String, Object>> excelDataMapList = new ArrayList<Map<String, Object>>();
            if (null == sensors || sensors.size() <= 0) {
                return;
            }

            DateTime startDt = new DateTime(NumberUtils.toLong(start));
            DateTime endDt = new DateTime(NumberUtils.toLong(end));

            for (VirtualSensor vs : sensors) {
                HashMap<String, Object> map = new HashMap<String, Object>();
                map.put("name", vs.getName());
                map.put("departmentName", vs.getDepartmentName());
                map.put("station_type", vs.getStation_type());
                map.put("ability", vs.getDay_deal_water_ability());
                map.put("setup_date", vs.getSetup_date());
                int onlineInt = vs.getOnline();
                String onlineText = "";
                if (onlineInt == EntityConst.SensorOnline.YES) {
                    onlineText = "在线";
                } else if (onlineInt == EntityConst.SensorOnline.NO) {
                    onlineText = "离线";
                } else if (onlineInt == EntityConst.SensorOnline.IDLE) {
                    onlineText = "发呆";
                } else {
                    onlineText = "未知";
                }

                map.put("online", onlineText);
                map.put("admin", vs.getAdmin());
                map.put("contact", vs.getContact());
                map.put("longitude", vs.getLongitude());
                map.put("latitude", vs.getLatitude());
                map.put("address", vs.getAddress());
                map.put("count", random.nextInt(Days.daysBetween(startDt, endDt).getDays()));
                excelDataMapList.add(map);
            }
            exportExcel("设备" + startDt.toString(TimeUtils.Pattern_Date) + "至" + endDt.toString(TimeUtils.Pattern_Date) + "QOS统计", "QOS统计", excelDataMapList,
                    new String[]{"站点名称", "所属机构", "站点类型", "日处水能力(吨)", "投产时间", "站点状态", "站点管理员", "联系电话", "经度", "纬度", "地址", "巡检次数"},
                    new String[]{"name", "departmentName", "station_type", "ability", "setup_date", "online", "admin", "contact", "longitude", "latitude", "address", "count"},
                    response);
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error(e.getMessage());
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("resp:{}", response);
        }
    }

    @RequestMapping(value = "/component/work", method = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT})
    @ResponseBody
    public Response componentFilterWork(
            @ModelAttribute("id") String id,
            @ModelAttribute("start") String start,
            @ModelAttribute("end") String end
    ) {
        Response response = null;
        try {
            Document result = new Document();
            VirtualSensor sensor = virtualSensorService.findById(id);
            if (null != sensor) {
                Date startDate = new Date(NumberUtils.toLong(start));
                Date endDate = new Date(NumberUtils.toLong(end));

                int record_count = 0;
                Document titlesDocument = new Document();
                Document valuesDocument = new Document();
                List<SensorDayMolErrorCountCencus> molList = cencusEntityService.findSensorDayMolErrorCountCencusByTimeRange(id, startDate, endDate);
                for (SensorDayMolErrorCountCencus cencus : molList) {
                    String date = TimeUtils.formatToDate(cencus.getTime());
                    Document document = (Document) valuesDocument.get(date);
                    if (null == document) {
                        document = new Document();
                        record_count++;
                    }
                    document.append("mol", cencus.getValue());
                    valuesDocument.put(date, document);
                }
                List<SensorDayElectricUseageCencus> elList = cencusEntityService.findSensorDayElectricUseageCencusByTimeRange(id, startDate, endDate);
                for (SensorDayElectricUseageCencus cencus : elList) {
                    String date = TimeUtils.formatToDate(cencus.getTime());
                    Document document = (Document) valuesDocument.get(date);
                    if (null == document) {
                        document = new Document();
                        record_count++;
                    }
                    document.append("el", cencus.getValue());
                    valuesDocument.put(date, document);
                }
                List<SensorDayHandlerWaterCencus> hwtList = cencusEntityService.findSensorDayHandlerWaterCencusByTimeRange(id, startDate, endDate);
                for (SensorDayHandlerWaterCencus cencus : hwtList) {
                    String date = TimeUtils.formatToDate(cencus.getTime());
                    Document document = (Document) valuesDocument.get(date);
                    if (null == document) {
                        document = new Document();
                        record_count++;
                    }
                    document.append("hwt", cencus.getValue());
                    valuesDocument.put(date, document);
                }

                for (Component component : sensor.getComponents()) {
                    String comp_name = component.getName();
                    if (StringUtils.equals(component.getType(), "onboard_controller")) {
                        List<ComponentDayWorkCencus> list = cencusEntityService.findComponentDayWorkCencusByTimeRange(id, component.getComp_id(), startDate, endDate);
                        for (ComponentDayWorkCencus cencus : list) {
                            String date = TimeUtils.formatToDate(cencus.getTime());
                            Document document = (Document) valuesDocument.get(date);
                            Document documentWt = null;
                            if (null == document) {
                                document = new Document();
                                record_count++;
                            } else {
                                documentWt = (Document) document.get("wt");
                            }
                            ArrayList<String> titleDoc = (ArrayList<String>) titlesDocument.get("wt");
                            if (null == titleDoc) {
                                titleDoc = new ArrayList<String>();
                            }
                            if (!titleDoc.contains(comp_name)) {
                                titleDoc.add(comp_name);
                            }
                            titlesDocument.put("wt", titleDoc);
                            if (null == documentWt) {
                                documentWt = new Document();
                            }
                            long current = 0;
                            if (documentWt.containsKey(comp_name)) {
                                current = TimeUtils.durationToMills(documentWt.getString(comp_name));
                            }
                            documentWt.append(comp_name, TimeUtils.millsToDuration(current + cencus.getValue()));
                            document.put("wt", documentWt);
                            valuesDocument.put(date, document);
                        }
                    } else if (component.getInstance_type() == 4105) {
                        List<SensorDayHighWaterLevelCountCencus> list = cencusEntityService.findSensorDayHighWaterLevelCountCencusByTimeRange(id, component.getComp_id(), startDate, endDate);
                        for (SensorDayHighWaterLevelCountCencus cencus : list) {
                            String date = TimeUtils.formatToDate(cencus.getTime());
                            Document document = (Document) valuesDocument.get(date);
                            Document documenthwl = null;
                            if (null == document) {
                                document = new Document();
                                record_count++;
                            } else {
                                documenthwl = (Document) document.get("hwl");
                            }
                            ArrayList<String> titleDoc = (ArrayList<String>) titlesDocument.get("hwl");
                            if (null == titleDoc) {
                                titleDoc = new ArrayList<String>();
                            }
                            if (!titleDoc.contains(comp_name)) {
                                titleDoc.add(comp_name);
                            }
                            titlesDocument.put("hwl", titleDoc);
                            if (null == documenthwl) {
                                documenthwl = new Document();
                            }
                            long current = 0;
                            if (documenthwl.containsKey(comp_name)) {
                                current = documenthwl.getLong(comp_name);
                            }
                            documenthwl.append(comp_name, current + cencus.getValue());
                            document.put("hwl", documenthwl);
                            valuesDocument.put(date, document);
                        }
                    }
                }
                result.put("titles", titlesDocument);
                result.put("values", valuesDocument);
                result.put("record_count", record_count);
            }
            response = makeResponse(ResponseCode.SUCCESS, result);
        } catch (Exception e) {
            response = makeResponse(ResponseCode.SYSTEM_ERROR);
            if (LOG.isErrorEnabled()) {
                LOG.error("start:{}, end:{}, error:{}", start, end, e.getMessage());
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("start:{}, end:{}, resp:{}", start, end, response);
        }
        return response;
    }

    @RequestMapping(value = "/component/work/export", method = RequestMethod.GET)
    public void componentFilterWorkExport(
            @ModelAttribute("id") String id,
            @ModelAttribute("start") String start,
            @ModelAttribute("end") String end,
            HttpServletResponse response
    ) {
        try {

            VirtualSensor sensor = virtualSensorService.findById(id);
            if (null == sensor) {
                return;
            }
            Date startDate = new Date(NumberUtils.toLong(start));
            Date endDate = new Date(NumberUtils.toLong(end));
            String dateTitleStart = TimeUtils.formatToDate(startDate);
            String dateTitleEnd = TimeUtils.formatToDate(endDate);

            ArrayList<Map<String, Object>> excelDataMapList = new ArrayList<Map<String, Object>>();
            HashMap<String, Map<String, Object>> excelMap = new HashMap<String, Map<String, Object>>();

            ArrayList<String> titleList = new ArrayList<String>();
            ArrayList<String> fieldList = new ArrayList<String>();
            titleList.add("日期");
            titleList.add("市电供电故障次数");
            titleList.add("日用电量");
            titleList.add("日处水量");
            fieldList.add("time");
            fieldList.add("mol");
            fieldList.add("el");
            fieldList.add("hwt");
            List<SensorDayMolErrorCountCencus> molList = cencusEntityService.findSensorDayMolErrorCountCencusByTimeRange(id, startDate, endDate);
            for (SensorDayMolErrorCountCencus cencus : molList) {
                String date = TimeUtils.formatToDate(cencus.getTime());
                Map<String, Object> document = excelMap.get(date);
                if (null == document) {
                    document = new Document();
                }
                document.put("mol", cencus.getValue());
                excelMap.put(date, document);
            }
            List<SensorDayElectricUseageCencus> elList = cencusEntityService.findSensorDayElectricUseageCencusByTimeRange(id, startDate, endDate);
            for (SensorDayElectricUseageCencus cencus : elList) {
                String date = TimeUtils.formatToDate(cencus.getTime());
                Map<String, Object> document = excelMap.get(date);
                if (null == document) {
                    document = new Document();
                }
                document.put("el", cencus.getValue());
                excelMap.put(date, document);
            }
            List<SensorDayHandlerWaterCencus> hwtList = cencusEntityService.findSensorDayHandlerWaterCencusByTimeRange(id, startDate, endDate);
            for (SensorDayHandlerWaterCencus cencus : hwtList) {
                String date = TimeUtils.formatToDate(cencus.getTime());
                Map<String, Object> document = excelMap.get(date);
                if (null == document) {
                    document = new Document();
                }
                document.put("hwt", cencus.getValue());
                excelMap.put(date, document);
            }

            for (Component component : sensor.getComponents()) {
                String comp_id = component.getComp_id();
                String comp_name = component.getName();
                if (StringUtils.equals(component.getType(), "onboard_controller")) {
                    List<ComponentDayWorkCencus> list = cencusEntityService.findComponentDayWorkCencusByTimeRange(id, component.getComp_id(), startDate, endDate);
                    for (ComponentDayWorkCencus cencus : list) {
                        String date = TimeUtils.formatToDate(cencus.getTime());
                        Map<String, Object> document = excelMap.get(date);
                        if (null == document) {
                            document = new Document();
                        }

                        long current = 0;
                        if (document.containsKey(comp_id)) {
                            current = TimeUtils.durationToMills((String) document.get(comp_id));
                        }
                        document.put(comp_id, TimeUtils.millsToDuration(current + cencus.getValue()));
                        if (!titleList.contains(comp_name + "运行时间")) {
                            titleList.add(comp_name + "运行时间");
                        }
                        if (!fieldList.contains(comp_id)) {
                            fieldList.add(comp_id);
                        }
                        excelMap.put(date, document);
                    }
                } else if (component.getInstance_type() == 4105) {
                    List<SensorDayHighWaterLevelCountCencus> list = cencusEntityService.findSensorDayHighWaterLevelCountCencusByTimeRange(id, component.getComp_id(), startDate, endDate);
                    for (SensorDayHighWaterLevelCountCencus cencus : list) {
                        String date = TimeUtils.formatToDate(cencus.getTime());
                        Map<String, Object> document = excelMap.get(date);
                        if (null == document) {
                            document = new Document();
                        }
//                        document.put(comp_id, cencus.getValue());
//                        titleList.add("高液位次数(" + comp_name + ")");
//                        fieldList.add(comp_id);

                        long current = 0;
                        if (document.containsKey(comp_id)) {
                            current = NumberUtils.toLong(document.get(comp_id).toString(), 0);
                        }
                        document.put(comp_id, current + cencus.getValue());
                        if (!titleList.contains("高液位次数(" + comp_name + ")")) {
                            titleList.add("高液位次数(" + comp_name + ")");
                        }
                        if (!fieldList.contains(comp_id)) {
                            fieldList.add(comp_id);
                        }

                        excelMap.put(date, document);
                    }
                }
            }
            String[] arrTitles = new String[titleList.size()];
            titleList.toArray(arrTitles);
            String[] arrFields = new String[fieldList.size()];
            fieldList.toArray(arrFields);

            for (Map.Entry<String, Map<String, Object>> entry : excelMap.entrySet()) {
                Map<String, Object> value = entry.getValue();
                value.put("time", entry.getKey());
                excelDataMapList.add(value);
            }

            exportExcel(sensor.getName() + "运行状态" + dateTitleStart + "至" + dateTitleEnd, "设备运行状态",
                    excelDataMapList, arrTitles, arrFields, response);
        } catch (Exception e) {
            if (LOG.isErrorEnabled()) {
                LOG.error(e.getMessage());
            }
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug("resp:{}", response);
        }
    }


    public static void main(String[] args) {
        System.out.println(new DateTime().minusDays(2).getMillis());
        System.out.println(new DateTime().getMillis());
    }

}
