package com.infinite.eoa.persistent.impl;

import com.infinite.eoa.core.persistent.MorphiaDAO;
import com.infinite.eoa.entity.Application;
import com.infinite.eoa.persistent.ApplicationDAO;
import org.mongodb.morphia.Datastore;

public class ApplicationDAOImpl extends MorphiaDAO<Application, String> implements ApplicationDAO {
    public ApplicationDAOImpl(Datastore ds) {
        super(ds);
    }

}
