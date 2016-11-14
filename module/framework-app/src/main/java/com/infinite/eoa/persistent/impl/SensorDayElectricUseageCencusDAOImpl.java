package com.infinite.eoa.persistent.impl;

import com.infinite.eoa.core.persistent.MorphiaDAO;
import com.infinite.eoa.entity.cencus.SensorDayHandlerWaterCencus;
import com.infinite.eoa.persistent.SensorDayHandlerWaterCencusDAO;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

public class SensorDayElectricUseageCencusDAOImpl extends MorphiaDAO<SensorDayHandlerWaterCencus, ObjectId> implements SensorDayHandlerWaterCencusDAO {
    public SensorDayElectricUseageCencusDAOImpl(Datastore ds) {
        super(ds);
    }

}
