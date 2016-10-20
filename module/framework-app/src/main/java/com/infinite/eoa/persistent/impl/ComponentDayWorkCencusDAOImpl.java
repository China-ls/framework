package com.infinite.eoa.persistent.impl;

import com.infinite.eoa.core.persistent.MorphiaDAO;
import com.infinite.eoa.entity.cencus.ComponentDayWorkCencus;
import com.infinite.eoa.persistent.ComponentDayWorkCencusDAO;
import org.mongodb.morphia.Datastore;

public class ComponentDayWorkCencusDAOImpl extends MorphiaDAO<ComponentDayWorkCencus, String> implements ComponentDayWorkCencusDAO {
    public ComponentDayWorkCencusDAOImpl(Datastore ds) {
        super(ds);
    }

}
