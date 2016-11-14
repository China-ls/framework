package com.infinite.eoa.persistent.impl;

import com.infinite.eoa.core.persistent.MorphiaDAO;
import com.infinite.eoa.entity.Department;
import com.infinite.eoa.persistent.DepartmentDAO;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

public class DepartmentDAOImpl extends MorphiaDAO<Department, ObjectId> implements DepartmentDAO {
    public DepartmentDAOImpl(Datastore ds) {
        super(ds);
    }

    @Override
    public Department findById(String id) {
        return findOne("id", new ObjectId(id));
    }

    @Override
    public Department findByEntity_id(String id) {
        return findOne("entity_id", id);
    }

    @Override
    public int deleteByEntityId(String id) {
        WriteResult result = deleteByQuery(createQuery().filter("entity_id", id));
        return result.getN();
    }
}

