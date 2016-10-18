package com.infinite.eoa.persistent.impl;

import com.infinite.eoa.core.persistent.MorphiaDAO;
import com.infinite.eoa.entity.VirtualSensorData;
import com.infinite.eoa.persistent.VirtualSensorDataDAO;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

public class VirtualSensorDataDAOImpl extends MorphiaDAO<VirtualSensorData, ObjectId> implements VirtualSensorDataDAO {

    public VirtualSensorDataDAOImpl(Datastore ds) {
        super(ds);
    }
}
