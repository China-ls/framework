package com.infinite.eoa.persistent.impl;

import com.infinite.eoa.core.persistent.MorphiaDAO;
import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.persistent.VirtualSensorDAO;
import org.mongodb.morphia.Datastore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VirtualSensorDAOImpl extends MorphiaDAO<VirtualSensor, String> implements VirtualSensorDAO {
    private static Logger LOG = LoggerFactory.getLogger(VirtualSensorDAOImpl.class);

    public VirtualSensorDAOImpl(Datastore ds) {
        super(ds);
    }

    @Override
    public VirtualSensor findById(String id) {
        if (LOG.isDebugEnabled()) {
            LOG.debug("findById:{}", id);
        }
        return findOne("sensor_id", id);
    }
}
