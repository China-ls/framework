package com.infinite.eoa.schedule;

import com.infinite.eoa.core.util.TimeUtils;
import com.infinite.eoa.entity.aggregation.SensorMaxMinAggregation;
import com.infinite.eoa.entity.cencus.SensorDayHandlerWaterCencus;
import com.infinite.eoa.persistent.SensorDayHandlerWaterCencusDAO;
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
public class CencusDayWaterHandlerSchedule {
    private static final Logger LOG = LoggerFactory.getLogger(CencusDayWaterHandlerSchedule.class);

    @Autowired
    private CencusService cencusService;
    @Autowired
    private SensorDayHandlerWaterCencusDAO dayHandlerWaterCencusDAO;

    @Scheduled(cron = "10 0 0 * * ?")
    public void cencus() {
        long start = System.currentTimeMillis();
        if (LOG.isDebugEnabled()) {
            LOG.debug("cencus day water handler");
        }
        try {
            DateTime date = new DateTime().minusDays(1).withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0);
            Date startTime = TimeUtils.getMinUtilDateOfDay(date);
            Date endTime = new DateTime().withHourOfDay(0).withMinuteOfHour(0).withSecondOfMinute(0).withMillisOfSecond(0).toDate();

            Iterator<SensorMaxMinAggregation> iterator = cencusService.cencusSensorWaterHandlerByTimeRange(startTime, endTime);
            while (iterator.hasNext()) {
                SensorMaxMinAggregation sensorMaxMinAggregation = iterator.next();
                SensorDayHandlerWaterCencus dayHandlerWaterCencus = new SensorDayHandlerWaterCencus();
                dayHandlerWaterCencus.setTime(startTime);
                dayHandlerWaterCencus.setSensor_id(sensorMaxMinAggregation.getId().getSensor_id());
                dayHandlerWaterCencus.setComp_id(sensorMaxMinAggregation.getId().getComp_id());
                dayHandlerWaterCencus.setValue(sensorMaxMinAggregation.getMax() - sensorMaxMinAggregation.getMin());
                dayHandlerWaterCencusDAO.save(dayHandlerWaterCencus);
            }
        } catch (Exception e) {
            LOG.error("cencus day water handler error.", e);
        }

        long end = System.currentTimeMillis();
        if (LOG.isDebugEnabled()) {
            LOG.debug("[cost: {}]", (end - start));
        }
    }

}
