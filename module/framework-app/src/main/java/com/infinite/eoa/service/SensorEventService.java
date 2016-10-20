package com.infinite.eoa.service;

import com.infinite.eoa.entity.SensorEvent;
import org.bson.Document;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface SensorEventService {
    public SensorEvent save(SensorEvent sensorEvent);

    SensorEvent findLatestBySensorId(String appkey, String sensor_id);

    Document cencusTodayAndMonthOnlineTimeBySensorId(String appkey, String id);
}
