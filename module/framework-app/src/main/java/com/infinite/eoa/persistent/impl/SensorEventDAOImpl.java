package com.infinite.eoa.persistent.impl;

import com.infinite.eoa.core.persistent.MorphiaDAO;
import com.infinite.eoa.entity.SensorEvent;
import com.infinite.eoa.persistent.SensorEventDAO;
import org.mongodb.morphia.Datastore;

public class SensorEventDAOImpl extends MorphiaDAO<SensorEvent, String> implements SensorEventDAO {
    public SensorEventDAOImpl(Datastore ds) {
        super(ds);
    }

    @Override
    public SensorEvent findFirstByTimeDescAndSensorId(String sensor_id) {
        return findOne(createQuery().filter("sensor_id", sensor_id).order("-time").limit(1));
    }
}
