package com.infinite.eoa.schedule;

import com.infinite.eoa.core.util.TimeUtils;
import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.persistent.VirtualSensorDAO;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Date;

public abstract class BasicYestodyCencusSchedule {
    private static final Logger LOG = LoggerFactory.getLogger(BasicYestodyCencusSchedule.class);

    @Autowired
    protected VirtualSensorDAO virtualSensorDAO;

    @Scheduled(cron = "0 0 0 * * ?")
    public void cencus() {
        long start = System.currentTimeMillis();
        if (LOG.isDebugEnabled()) {
            LOG.debug("cencus sensor component work time");
        }
        try {
            DateTime date = new DateTime().minusDays(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
            Date startDate = TimeUtils.getMinUtilDateOfDay(date);
            Date endDate = new DateTime().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).toDate();

            for (VirtualSensor sensor : virtualSensorDAO.find()) {
                cencusSensor(sensor, startDate, endDate);
            }
        } catch (Exception e) {
            LOG.error("cencus component day work error.", e);
        }

        long end = System.currentTimeMillis();
        if (LOG.isDebugEnabled()) {
            LOG.debug("[cost: {}]", (end - start));
        }
    }

    abstract void cencusSensor(VirtualSensor sensor, Date startDate, Date endDate);

}
