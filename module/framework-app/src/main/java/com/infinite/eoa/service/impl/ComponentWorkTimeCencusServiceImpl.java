package com.infinite.eoa.service.impl;

import com.infinite.eoa.core.util.TimeUtils;
import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.entity.VirtualSensorData;
import com.infinite.eoa.entity.cencus.ComponentDayWorkCencus;
import com.infinite.eoa.persistent.ComponentDayWorkCencusDAO;
import com.infinite.eoa.persistent.VirtualSensorDAO;
import com.infinite.eoa.persistent.VirtualSensorDataDAO;
import com.infinite.eoa.service.ComponentWorkTimeCencusService;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.bson.Document;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ComponentWorkTimeCencusServiceImpl implements ComponentWorkTimeCencusService {
    @Autowired
    private ComponentDayWorkCencusDAO componentDayWorkCencusDAO;
    @Autowired
    private VirtualSensorDataDAO virtualSensorDataDAO;
    @Autowired
    private VirtualSensorDAO virtualSensorDAO;

    @Override
    public Document getDayAndMonthBySensorId(String sensor_id) {
        VirtualSensor sensor = virtualSensorDAO.findById(sensor_id);
        if (null == sensor) {
            return null;
        }
        Document document = new Document();
        Document month = new Document();
        Document day = new Document();
        List<ComponentDayWorkCencus> list = componentDayWorkCencusDAO.find(
                componentDayWorkCencusDAO.createQuery()
                        .filter("sensor_id", sensor_id)
                        .field("time").greaterThanOrEq(TimeUtils.getMinUtilDateOfThisMonth())
        ).asList();
        if (null != list) {
            for (ComponentDayWorkCencus cencus : list) {
                long total = month.containsKey(cencus.getComp_id()) ? NumberUtils.toLong(month.get(cencus.getComp_id()).toString()) : 0;
                month.put(cencus.getComp_id(), total + cencus.getWork());
            }
        }
        DateTime dateTime = new DateTime();
        long startTime = TimeUtils.getMinMillsOfDay(dateTime);
        long endTime = dateTime.getMillis();
        Date date = dateTime.toDate();

        Collection<ComponentDayWorkCencus> cencusCollection = cencusSensorComponent(startTime, endTime, date, sensor);
        for (ComponentDayWorkCencus cencus : cencusCollection) {
            long total = month.containsKey(cencus.getComp_id()) ? NumberUtils.toLong(month.get(cencus.getComp_id()).toString()) : 0;
            day.put(cencus.getComp_id(), TimeUtils.formatToDays(cencus.getWork()));
            Long def = month.containsKey(cencus.getComp_id()) ? NumberUtils.toLong(month.get(cencus.getComp_id()).toString()) : 0;
            month.put(cencus.getComp_id(), TimeUtils.formatToDays(total + def));
        }
        document.put("month", month);
        document.put("day", day);
        return document;
    }

    public Collection<ComponentDayWorkCencus> cencusSensorComponent(long startTime, long endTime, Date date, VirtualSensor sensor) {
        ArrayList<ComponentDayWorkCencus> cencusList = new ArrayList<ComponentDayWorkCencus>();
        for (com.infinite.eoa.entity.Component comp : sensor.getComponents()) {
            String comp_type = comp.getType();
            if (!StringUtils.equals("onboard_controller", comp_type)
                    /*|| !StringUtils.equals("onboard_controller", comp_type)*/) {
                continue;
            }
            Date on = null;
            long distance = 0;

            Iterator<VirtualSensorData> sensorDatas = virtualSensorDataDAO.find(
                    virtualSensorDataDAO.createQuery()
                            .filter("sensor_id", sensor.getSensor_id())
                            .filter("comp_id", comp.getComp_id())
                            .field("time").greaterThanOrEq(new Date(startTime))
                            .field("time").lessThan(new Date(endTime))
                            .order("time")
            ).iterator();

            boolean latestIsOn = false;
            while (sensorDatas.hasNext()) {
                VirtualSensorData sensorData = sensorDatas.next();
                if (sensorData.isOnoff()) {
                    if (latestIsOn) {
                        continue;
                    }
                    latestIsOn = true;
                    on = sensorData.getTime();
                    if (!sensorDatas.hasNext()) {
                        distance += endTime - on.getTime();
                    }
                } else {
                    if (!latestIsOn) {
                        continue;
                    }
                    latestIsOn = false;
                    if (on == null) {
                        distance = sensorData.getTime().getTime() - startTime;
                    } else {
                        distance += sensorData.getTime().getTime() - on.getTime();
                    }
                }
            }

            ComponentDayWorkCencus cencus = new ComponentDayWorkCencus();
            cencus.setComp_id(comp.getComp_id());
            cencus.setSensor_id(sensor.getSensor_id());
            cencus.setTime(date);
            cencus.setWork(distance);

            cencusList.add(cencus);
        }
        return cencusList;
    }

    public static void main(String[] args) {
//        System.out.println( new DateTime(86384000).toString("dd天HH小时mm分ss秒") );
        System.out.println( TimeUtils.formatToDays(86384000) );
    }

}
