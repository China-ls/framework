package com.infinite.eoa.persistent.impl;

import com.infinite.eoa.core.persistent.MorphiaDAO;
import com.infinite.eoa.entity.cencus.SensorDayHighWaterLevelCountCencus;
import com.infinite.eoa.persistent.SensorDayHighWaterLevelCountCencusDAO;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

public class SensorDayHighWaterLevelCountCencusDAOImpl extends MorphiaDAO<SensorDayHighWaterLevelCountCencus, ObjectId>
        implements SensorDayHighWaterLevelCountCencusDAO {
    public SensorDayHighWaterLevelCountCencusDAOImpl(Datastore ds) {
        super(ds);
    }

}
