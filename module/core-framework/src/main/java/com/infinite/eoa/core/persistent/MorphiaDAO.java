package com.infinite.eoa.core.persistent;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.dao.BasicDAO;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @param <T> the type of the entity
 * @param <K> the type of the key
 */
public class MorphiaDAO<T, K> extends BasicDAO<T, K> implements IMorphiaDAO<T,K> {
    @Autowired
    public MorphiaDAO(Datastore ds) {
        super(ds);
    }

}
