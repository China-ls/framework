package com.infinite.eoa.schedule;

import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.entity.cencus.ComponentDayWorkCencus;
import com.infinite.eoa.persistent.ComponentDayWorkCencusDAO;
import com.infinite.eoa.service.CencusService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;

@Component
public class CencusComponentWorkTimeSchedule extends BasicYestodyCencusSchedule{
    private static final Logger LOG = LoggerFactory.getLogger(CencusComponentWorkTimeSchedule.class);

    @Autowired
    private ComponentDayWorkCencusDAO componentDayWorkCencusDAO;
    @Autowired
    private CencusService cencusService;

    @Override
    void cencusSensor(VirtualSensor sensor, Date startDate, Date endDate) {
        ArrayList<ComponentDayWorkCencus> cencuses = cencusService.cencusSensorComponentWorkTimeByTimeRange(sensor, startDate, endDate);
        for (ComponentDayWorkCencus cencus : cencuses) {
            componentDayWorkCencusDAO.save(cencus);
        }
    }

}
