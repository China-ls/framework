package com.infinite.eoa.persistent.impl;

import com.infinite.eoa.core.persistent.MorphiaDAO;
import com.infinite.eoa.entity.cencus.SensorDayMolErrorCountCencus;
import com.infinite.eoa.persistent.SensorDayMolErrorCountCencusDAO;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

public class SensorDayMolErrorCountCencusDAOImpl extends MorphiaDAO<SensorDayMolErrorCountCencus, ObjectId> implements SensorDayMolErrorCountCencusDAO {
    public SensorDayMolErrorCountCencusDAOImpl(Datastore ds) {
        super(ds);
    }

}
