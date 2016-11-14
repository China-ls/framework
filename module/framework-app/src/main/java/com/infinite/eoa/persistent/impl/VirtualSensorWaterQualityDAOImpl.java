package com.infinite.eoa.persistent.impl;

import com.infinite.eoa.core.persistent.MorphiaDAO;
import com.infinite.eoa.entity.VirtualSensorWaterQuality;
import com.infinite.eoa.persistent.VirtualSensorWaterQualityDAO;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

public class VirtualSensorWaterQualityDAOImpl extends MorphiaDAO<VirtualSensorWaterQuality, ObjectId> implements VirtualSensorWaterQualityDAO {

    public VirtualSensorWaterQualityDAOImpl(Datastore ds) {
        super(ds);
    }
}
