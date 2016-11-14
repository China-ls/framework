package com.infinite.eoa.router.service;

import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.entity.aggregation.SensorMaxMinAggregation;
import com.infinite.eoa.entity.cencus.SensorDayHighWaterLevelCountCencus;
import com.infinite.eoa.entity.cencus.SensorDayMolErrorCountCencus;
import com.infinite.eoa.schedule.CencusComponentWorkTimeSchedule;
import com.infinite.eoa.schedule.CencusDayHighWaterLevelSchedule;
import com.infinite.eoa.schedule.CencusDayWaterHandlerSchedule;
import com.infinite.eoa.schedule.CencusElectricUseageSchedule;
import com.infinite.eoa.schedule.CencusSensorMainsOnLoadDayErrorSchedule;
import com.infinite.eoa.service.CencusService;
import com.infinite.eoa.service.VirtualSensorService;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration({
        "classpath:/spring/application-context.xml",
        "classpath:/spring/application-schedule.xml",
        "classpath:/spring/application-service.xml",
        "classpath:/spring/application-jms.xml",
        "classpath:/spring/application-thread-pool.xml",
        "classpath:/spring/application-dao.xml"
})
public class CencusServiceTest {

    @Autowired
    private VirtualSensorService virtualSensorService;
    @Autowired
    private CencusService cencusService;
    @Autowired
    private CencusDayHighWaterLevelSchedule cencusDayHighWaterLevelSchedule;
    @Autowired
    private CencusDayWaterHandlerSchedule cencusDayWaterHandlerSchedule;
    @Autowired
    private CencusElectricUseageSchedule cencusElectricUseageSchedule;
    @Autowired
    private CencusSensorMainsOnLoadDayErrorSchedule cencusSensorMainsOnLoadDayErrorSchedule;
    @Autowired
    private CencusComponentWorkTimeSchedule cencusComponentWorkTimeSchedule;

    @Test
    public void testCencusDayWaterHanlder() {
        DateTime dateTime = new DateTime();
        Date startDate = dateTime.minusDays(1).withTime(0, 0, 0, 0).toDate();
        Date endDate = dateTime.withTime(0, 0, 0, 0).toDate();
        Iterator<SensorMaxMinAggregation> iterator = cencusService.cencusSensorWaterHandlerByTimeRange(startDate, endDate);
        while (iterator.hasNext()) {
            SensorMaxMinAggregation agg = iterator.next();
            System.out.println(agg);
        }

        cencusDayWaterHandlerSchedule.cencus();
    }

    @Test
    public void testCencusSensorHighWaterLevelByTimeRange() {
        DateTime dateTime = new DateTime();
        Date startDate = dateTime.minusDays(1).withTime(0, 0, 0, 0).toDate();
        Date endDate = dateTime.withTime(0, 0, 0, 0).toDate();
        for (VirtualSensor sensor : virtualSensorService.find()) {
            ArrayList<SensorDayHighWaterLevelCountCencus> list = cencusService.cencusSensorHighWaterLevelByTimeRange(sensor, startDate, endDate);
            for (SensorDayHighWaterLevelCountCencus cencus : list) {
                System.out.println(cencus);
            }
        }
        cencusDayHighWaterLevelSchedule.cencus();
    }

    @Test
    public void testCencusSensorMainonloadErrorCountByTimeRange() {
        DateTime dateTime = new DateTime();
        Date startDate = dateTime.minusDays(1).withTime(0, 0, 0, 0).toDate();
        Date endDate = dateTime.withTime(0, 0, 0, 0).toDate();
        for (VirtualSensor sensor : virtualSensorService.find()) {
            ArrayList<SensorDayMolErrorCountCencus> list = cencusService.cencusSensorMainonloadErrorCountByTimeRange(sensor, startDate, endDate);
            for (SensorDayMolErrorCountCencus cencus : list) {
                System.out.println(cencus);
            }
        }
        cencusSensorMainsOnLoadDayErrorSchedule.cencus();
    }

    @Test
    public void testCencusElectricUseage() {
        cencusElectricUseageSchedule.cencus();
    }

    @Test
    public void testCencusComponentWorkTime() {
        cencusComponentWorkTimeSchedule.cencus();
    }

}
