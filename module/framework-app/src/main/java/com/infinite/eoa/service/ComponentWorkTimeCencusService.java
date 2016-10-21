package com.infinite.eoa.service;

import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.entity.cencus.ComponentDayWorkCencus;
import org.bson.Document;

import java.util.Collection;
import java.util.Date;

public interface ComponentWorkTimeCencusService {

    public Document getDayAndMonthBySensorId(String sensor_id);

    Collection<ComponentDayWorkCencus> cencusSensorComponent(long startTime, long endTime, Date date, VirtualSensor sensor);

}
