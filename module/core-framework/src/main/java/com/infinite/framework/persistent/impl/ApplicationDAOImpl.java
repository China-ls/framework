package com.infinite.framework.persistent.impl;

import com.infinite.framework.core.persistent.MorphiaDAO;
import com.infinite.framework.entity.Application;
import com.infinite.framework.persistent.ApplicationDAO;
import org.mongodb.morphia.Datastore;

public class ApplicationDAOImpl extends MorphiaDAO<Application, String> implements ApplicationDAO {
    public ApplicationDAOImpl(Datastore ds) {
        super(ds);
    }

}
