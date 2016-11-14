package com.infinite.eoa.schedule;

import com.infinite.eoa.core.util.TimeUtils;
import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.entity.cencus.SensorDayMolErrorCountCencus;
import com.infinite.eoa.persistent.SensorDayMolErrorCountCencusDAO;
import com.infinite.eoa.persistent.VirtualSensorDAO;
import com.infinite.eoa.service.CencusService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

/**
 * 定时 计算每天 设备 市政供电故障次数
 */
@Component
public class CencusSensorMainsOnLoadDayErrorSchedule {
    private static final Logger LOG = LoggerFactory.getLogger(CencusSensorMainsOnLoadDayErrorSchedule.class);

    @Autowired
    private VirtualSensorDAO virtualSensorDAO;
    @Autowired
    private SensorDayMolErrorCountCencusDAO sensorDayMolErrorCountCencusDAO;
    @Autowired
    private CencusService cencusService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void cencus() {
        long start = System.currentTimeMillis();
        if (LOG.isDebugEnabled()) {
            LOG.debug("cencus sensor main on load day errorcount");
        }
        try {
            DateTime date = new DateTime().minusDays(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
            Date startDate = TimeUtils.getMinUtilDateOfDay(date);
            Date endDate = new DateTime().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).toDate();

            for (VirtualSensor sensor : virtualSensorDAO.find()) {
                cencusSensor(sensor, startDate, endDate);
            }
        } catch (Exception e) {
            LOG.error("cencus sensor main on load day errorcount error.", e);
        }

        long end = System.currentTimeMillis();
        if (LOG.isDebugEnabled()) {
            LOG.debug("[cost: {}]", (end - start));
        }
    }

    private void cencusSensor(VirtualSensor sensor, Date startDate, Date endDate) {
        ArrayList<SensorDayMolErrorCountCencus> list = cencusService.cencusSensorMainonloadErrorCountByTimeRange(sensor.getSensor_id(), startDate, endDate);
        for (SensorDayMolErrorCountCencus cencus : list) {
            sensorDayMolErrorCountCencusDAO.save(cencus);
        }
    }

}
