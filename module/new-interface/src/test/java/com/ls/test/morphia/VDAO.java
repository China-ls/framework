package com.ls.test.morphia;

import com.infinite.framework.core.persistent.IMorphiaDAO;
import com.infinite.framework.core.persistent.MorphiaDAO;
import org.mongodb.morphia.Datastore;

public class VDAO extends MorphiaDAO<V, String> implements IMorphiaDAO<V, String> {
    public VDAO(Datastore ds) {
        super(ds);
    }
}
