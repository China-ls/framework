package com.infinite.eoa.schedule;

import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.entity.cencus.SensorDayHighWaterLevelCountCencus;
import com.infinite.eoa.persistent.SensorDayHighWaterLevelCountCencusDAO;
import com.infinite.eoa.service.CencusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@Component
public class CencusDayHighWaterLevelSchedule extends BasicYestodyCencusSchedule {
    private static final Logger LOG = LoggerFactory.getLogger(CencusDayHighWaterLevelSchedule.class);

    @Autowired
    private CencusService cencusService;
    @Autowired
    private SensorDayHighWaterLevelCountCencusDAO dayHighWaterLevelCountCencusDAO;

    @Override
    void cencusSensor(VirtualSensor sensor, Date startDate, Date endDate) {
        ArrayList<SensorDayHighWaterLevelCountCencus> cencusArrayList = cencusService.cencusSensorHighWaterLevelByTimeRange(sensor, startDate, endDate);
        for (SensorDayHighWaterLevelCountCencus cencus : cencusArrayList) {
            dayHighWaterLevelCountCencusDAO.save(cencus);
        }
    }

}
