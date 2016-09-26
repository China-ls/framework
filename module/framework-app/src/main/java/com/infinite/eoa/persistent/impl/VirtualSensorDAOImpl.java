package com.infinite.eoa.persistent.impl;

import com.infinite.eoa.core.persistent.MorphiaDAO;
import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.persistent.VirtualSensorDAO;
import org.mongodb.morphia.Datastore;

public class VirtualSensorDAOImpl extends MorphiaDAO<VirtualSensor, String> implements VirtualSensorDAO {
    public VirtualSensorDAOImpl(Datastore ds) {
        super(ds);
    }

    @Override
    public VirtualSensor findById(String id) {
        return findOne("sensor_id", id);
    }
}
