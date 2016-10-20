package com.infinite.eoa.schedule;

import com.infinite.eoa.core.util.TimeUtils;
import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.entity.VirtualSensorData;
import com.infinite.eoa.entity.cencus.ComponentDayWorkCencus;
import com.infinite.eoa.persistent.ComponentDayWorkCencusDAO;
import com.infinite.eoa.persistent.VirtualSensorDAO;
import com.infinite.eoa.persistent.VirtualSensorDataDAO;
import org.joda.time.DateTime;
import org.mongodb.morphia.query.MorphiaKeyIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Component
public class CencusSchedule {
    private static final Logger LOG = LoggerFactory.getLogger(CencusSchedule.class);

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

        }
        try {
            MorphiaKeyIterator<VirtualSensor> sensorIds = virtualSensorDAO.find().fetchKeys();
            while (sensorIds.hasNext()) {
                String id = (String) sensorIds.next().getId();
                LOG.debug("id:{}", id);
                Collection<ComponentDayWorkCencus> cencusCollection = cencusSensorComponent(id);
                for (ComponentDayWorkCencus cencus : cencusCollection) {
                    componentDayWorkCencusDAO.save(cencus);
                }
            }
            List<ComponentDayWorkCencus> list = componentDayWorkCencusDAO.find().asList();
            for (ComponentDayWorkCencus cencus : list) {
                LOG.debug("[{}]", cencus);
            }
        } catch (Exception e) {
            LOG.error("cencus component day work error.", e);
        }

        long end = System.currentTimeMillis();
        if (LOG.isDebugEnabled()) {
            LOG.debug("[cost: {}]", (end - start));
        }
    }

    private Collection<ComponentDayWorkCencus> cencusSensorComponent(String sensorid) {
        DateTime dateTime = new DateTime();
        long endTime = TimeUtils.getMinMillsOfDay(dateTime);
        dateTime = dateTime.minusDays(1);
        long startTime = TimeUtils.getMinMillsOfDay(dateTime);
        Date date = dateTime.toDate();

        HashMap<String, Date> latestCencus = new HashMap<String, Date>();
        HashMap<String, ComponentDayWorkCencus> dayCencus = new HashMap<String, ComponentDayWorkCencus>();
        for (VirtualSensorData sensorData : virtualSensorDataDAO.find(
                virtualSensorDataDAO.createQuery()
                        .filter("sensor_id", sensorid)
                        .field("time").greaterThanOrEq(new Date(startTime))
                        .field("time").lessThan(new Date(endTime))
                        .order("comp_type,time")
        )) {
            String cid = sensorData.getComp_id();
            Date time = sensorData.getTime();
            long ct = time.getTime();
            Date lateast = latestCencus.get(cid);
            if (sensorData.isOnoff()) {//开启的
                if (null == lateast) {
                    latestCencus.put(cid, time);
                }
            } else if (!sensorData.isOnoff() && null != lateast) {//关闭的
                long distance = ct - lateast.getTime();
                ComponentDayWorkCencus def = dayCencus.get(cid);
                if (null == def) {
                    def = new ComponentDayWorkCencus();
                    def.setSensor_id(sensorid);
                    def.setComp_id(cid);
                    def.setTime(date);
                }
                def.setWork(def.getWork() + distance);
                dayCencus.put(cid, def);
                latestCencus.remove(cid);
            }
        }
        LOG.debug("CENCUS:{}", dayCencus);
        return dayCencus.values();
    }

}
