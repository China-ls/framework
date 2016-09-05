package com.infinite.framework.persistent.impl;

import com.infinite.framework.core.persistent.MorphiaDAO;
import com.infinite.framework.entity.VirtualSensor;
import com.infinite.framework.persistent.VirtualSensorDAO;
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
