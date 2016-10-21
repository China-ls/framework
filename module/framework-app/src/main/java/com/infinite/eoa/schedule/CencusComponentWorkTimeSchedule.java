package com.infinite.eoa.schedule;

import com.infinite.eoa.core.util.TimeUtils;
import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.entity.VirtualSensorData;
import com.infinite.eoa.entity.cencus.ComponentDayWorkCencus;
import com.infinite.eoa.persistent.ComponentDayWorkCencusDAO;
import com.infinite.eoa.persistent.VirtualSensorDAO;
import com.infinite.eoa.persistent.VirtualSensorDataDAO;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class CencusComponentWorkTimeSchedule {
    private static final Logger LOG = LoggerFactory.getLogger(CencusComponentWorkTimeSchedule.class);

    @Autowired
    private VirtualSensorDAO virtualSensorDAO;
    @Autowired
    private ComponentDayWorkCencusDAO componentDayWorkCencusDAO;
    @Autowired
    private VirtualSensorDataDAO virtualSensorDataDAO;

    @Scheduled(cron = "0 0 0 * * ?")
    public void cencus() {
        long start = System.currentTimeMillis();
        if (LOG.isDebugEnabled()) {
            LOG.debug("cencus sensor component work time");
        }
        try {
            List<VirtualSensor> sensors = virtualSensorDAO.find().asList();

            DateTime date = new DateTime().minusDays(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
            long startTime = TimeUtils.getMinMillsOfDay(date);
            long endTime = new DateTime().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).getMillis();

            for (VirtualSensor sensor : sensors) {
                Collection<ComponentDayWorkCencus> cencusCollection = cencusSensorComponent(startTime, endTime, date.toDate(), sensor);
                for (ComponentDayWorkCencus cencus : cencusCollection) {
                    componentDayWorkCencusDAO.save(cencus);
                }
            }
        } catch (Exception e) {
            LOG.error("cencus component day work error.", e);
        }

        long end = System.currentTimeMillis();
        if (LOG.isDebugEnabled()) {
            LOG.debug("[cost: {}]", (end - start));
        }
    }

    private Collection<ComponentDayWorkCencus> cencusSensorComponent(long startTime, long endTime, Date date, VirtualSensor sensor) {
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
                    if (latestIsOn && sensorDatas.hasNext()) {
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
            LOG.debug("CENCUS:[date:{}, time:{}]", date.toLocaleString(), TimeUtils.formatToDays(distance));
            cencusList.add(cencus);
        }
        return cencusList;
    }

}
