package com.infinite.eoa.service;

import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.entity.aggregation.SensorMaxMinAggregation;
import com.infinite.eoa.entity.aggregation.VsDeviceStatusAggregation;
import com.infinite.eoa.entity.aggregation.VsDeviceTypeAggregation;
import com.infinite.eoa.entity.cencus.ComponentDayWorkCencus;
import com.infinite.eoa.entity.cencus.SensorDayHighWaterLevelCountCencus;
import com.infinite.eoa.entity.cencus.SensorDayMolErrorCountCencus;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface CencusService {
    public ArrayList<VsDeviceTypeAggregation> deviceTypeDistribution();

    public ArrayList<VsDeviceStatusAggregation> deviceOnlineDistribution();

    HashMap<String, Double> cencusPositiveTotalByStation_type(String station_type, long start, long end);

    HashMap<String, Double> cencusPowerByStation_type(String station_type, long start, long end);

    Iterator<SensorMaxMinAggregation> cencusSensorElectricUseageByTimeRange(Date startTime, Date endTime);

    SensorMaxMinAggregation cencusSensorElectricUseageBySensorAndTimeRange(String sensor_id, Date startTime, Date endTime);

    SensorMaxMinAggregation cencusSensorElectricUseageBySensorAndTimeRange(VirtualSensor sensor, Date startTime, Date endTime);

    Iterator<SensorMaxMinAggregation> cencusSensorWaterHandlerByTimeRange(Date startTime, Date endTime);

    SensorMaxMinAggregation cencusSensorWaterHandlerBySensorAndTimeRange(String sensor_id, Date startTime, Date endTime);

    SensorMaxMinAggregation cencusSensorWaterHandlerBySensorAndTimeRange(VirtualSensor sensor, Date startTime, Date endTime);

    ArrayList<SensorDayHighWaterLevelCountCencus> cencusSensorHighWaterLevelByTimeRange(String sensor_id, Date startTime, Date endTime);

    ArrayList<SensorDayHighWaterLevelCountCencus> cencusSensorHighWaterLevelByTimeRange(VirtualSensor sensor, Date startTime, Date endTime);

    ArrayList<SensorDayMolErrorCountCencus> cencusSensorMainonloadErrorCountByTimeRange(String sensor_id, Date startTime, Date endTime);

    ArrayList<SensorDayMolErrorCountCencus> cencusSensorMainonloadErrorCountByTimeRange(VirtualSensor sensor, Date startTime, Date endTime);

    ArrayList<ComponentDayWorkCencus> cencusSensorComponentWorkTimeByTimeRange(String sensor_id, Date startTime, Date endTime);

    ArrayList<ComponentDayWorkCencus> cencusSensorComponentWorkTimeByTimeRange(VirtualSensor sensor, Date startTime, Date endTime);

}
