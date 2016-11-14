package com.infinite.eoa.persistent.impl;

import com.infinite.eoa.core.persistent.MorphiaDAO;
import com.infinite.eoa.entity.cencus.SensorDayElectricUseageCencus;
import com.infinite.eoa.persistent.SensorDayElectricUseageCencusDAO;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

public class SensorDayHandlerWaterCencusDAOImpl extends MorphiaDAO<SensorDayElectricUseageCencus, ObjectId> implements SensorDayElectricUseageCencusDAO {
    public SensorDayHandlerWaterCencusDAOImpl(Datastore ds) {
        super(ds);
    }

}
