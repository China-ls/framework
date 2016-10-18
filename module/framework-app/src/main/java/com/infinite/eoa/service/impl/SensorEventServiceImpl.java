package com.infinite.eoa.service.impl;

import com.infinite.eoa.entity.SensorEvent;
import com.infinite.eoa.persistent.SensorEventDAO;
import com.infinite.eoa.service.SensorEventService;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SensorEventServiceImpl implements SensorEventService {
    @Autowired
    private SensorEventDAO sensorEventDAO;

    @Override
    public SensorEvent save(SensorEvent sensorEvent) {
        Key<SensorEvent> key = sensorEventDAO.save(sensorEvent);
        sensorEvent.setId((ObjectId) key.getId());
        return sensorEvent;
    }

    @Override
    public SensorEvent findLatestBySensorId(String appkey, String sensor_id) {
        return sensorEventDAO.findFirstByTimeDescAndSensorId(sensor_id);
    }
}
