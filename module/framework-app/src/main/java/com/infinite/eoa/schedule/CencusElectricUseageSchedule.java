package com.infinite.eoa.schedule;

import com.infinite.eoa.core.util.TimeUtils;
import com.infinite.eoa.entity.aggregation.SensorMaxMinAggregation;
import com.infinite.eoa.entity.cencus.SensorDayElectricUseageCencus;
import com.infinite.eoa.persistent.SensorDayElectricUseageCencusDAO;
import com.infinite.eoa.service.CencusService;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Iterator;

@Component
public class CencusElectricUseageSchedule {
    private static final Logger LOG = LoggerFactory.getLogger(CencusElectricUseageSchedule.class);

    @Autowired
    private CencusService cencusService;
    @Autowired
    private SensorDayElectricUseageCencusDAO sensorDayElectricUseageCencusDAO;

    @Scheduled(cron = "0 0 0 * * ?")
    public void cencus() {
        long start = System.currentTimeMillis();
        if (LOG.isDebugEnabled()) {
            LOG.debug("cencus electric useage");
        }
        try {
            DateTime date = new DateTime().minusDays(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
            Date startTime = TimeUtils.getMinUtilDateOfDay(date);
            Date endTime = new DateTime().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).toDate();

            Iterator<SensorMaxMinAggregation> iterator = cencusService.cencusSensorElectricUseageByTimeRange(startTime, endTime);
            while (iterator.hasNext()) {
                SensorMaxMinAggregation sensorMaxMinAggregation = iterator.next();
                SensorDayElectricUseageCencus electricDayUseageEntity = new SensorDayElectricUseageCencus();
                electricDayUseageEntity.setTime(startTime);
                electricDayUseageEntity.setSensor_id(sensorMaxMinAggregation.getId().getSensor_id());
                electricDayUseageEntity.setComp_id(sensorMaxMinAggregation.getId().getComp_id());
                electricDayUseageEntity.setValue(sensorMaxMinAggregation.getMax() - sensorMaxMinAggregation.getMin());
                sensorDayElectricUseageCencusDAO.save(electricDayUseageEntity);
            }
        } catch (Exception e) {
            LOG.error("cencus electric useage error.", e);
        }

        long end = System.currentTimeMillis();
        if (LOG.isDebugEnabled()) {
            LOG.debug("[cost: {}]", (end - start));
        }
    }

}
