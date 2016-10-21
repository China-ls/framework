package com.infinite.eoa.schedule;

import com.infinite.eoa.core.util.TimeUtils;
import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.entity.cencus.ComponentDayWorkCencus;
import com.infinite.eoa.persistent.ComponentDayWorkCencusDAO;
import com.infinite.eoa.persistent.VirtualSensorDAO;
import com.infinite.eoa.service.ComponentWorkTimeCencusService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Collection;
import java.util.Date;
import java.util.List;

//@Component
public class CencusPositiveTotalSchedule {
    private static final Logger LOG = LoggerFactory.getLogger(CencusPositiveTotalSchedule.class);

    @Autowired
    private VirtualSensorDAO virtualSensorDAO;
    @Autowired
    private ComponentDayWorkCencusDAO componentDayWorkCencusDAO;
    @Autowired
    private ComponentWorkTimeCencusService componentWorkTimeCencusService;


    @Scheduled(cron = "0 0 0 * * ?")
    public void cencus() {
        long start = System.currentTimeMillis();
        if (LOG.isDebugEnabled()) {
            LOG.debug("cencus sensor component work time");
        }
        try {
            List<VirtualSensor> sensors = virtualSensorDAO.find().asList();

            DateTime dateTime = new DateTime();
            long endTime = TimeUtils.getMinMillsOfDay(dateTime);
            dateTime = dateTime.minusDays(1);
            long startTime = TimeUtils.getMinMillsOfDay(dateTime);
            Date date = dateTime.toDate();

            for (VirtualSensor sensor : sensors) {
                Collection<ComponentDayWorkCencus> cencusCollection = componentWorkTimeCencusService.cencusSensorComponent(startTime, endTime, date, sensor);
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

}
