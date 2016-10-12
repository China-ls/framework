package com.infinite.eoa.persistent.impl;

import com.infinite.eoa.core.persistent.MorphiaDAO;
import com.infinite.eoa.entity.Role;
import com.infinite.eoa.persistent.RoleDAO;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

public class RoleDAOImpl extends MorphiaDAO<Role, ObjectId> implements RoleDAO {
    public RoleDAOImpl(Datastore ds) {
        super(ds);
    }

    @Override
    public Role findByname(String name) {
        return findOne("name", name);
    }

    @Override
    public Role findById(String id) {
        if (null == id || id.length() <= 0) {
            return null;
        }
        return findOne("id", new ObjectId(id));
    }
}
