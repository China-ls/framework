package com.infinite.eoa.persistent.impl;

import com.infinite.eoa.core.persistent.MorphiaDAO;
import com.infinite.eoa.entity.DepartmentEntity;
import com.infinite.eoa.persistent.DepartmentEntityDAO;
import com.mongodb.WriteResult;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

public class DepartmentEntityDAOImpl extends MorphiaDAO<DepartmentEntity, ObjectId> implements DepartmentEntityDAO {
    public DepartmentEntityDAOImpl(Datastore ds) {
        super(ds);
    }

    @Override
    public int deleteById(String id) {
        WriteResult results = deleteByQuery(
                createQuery().filter("id", new ObjectId(id))
        );
        return results.getN();
    }

    @Override
    public int deleteByDepartmentId(String department_id) {
        WriteResult results = deleteByQuery(
                createQuery().filter("department_id", new ObjectId(department_id))
        );
        return results.getN();
    }

    @Override
    public int deleteByEntityId(String entity_id) {
        WriteResult results = deleteByQuery(
                createQuery().filter("entity_id", new ObjectId(entity_id))
        );
        return results.getN();
    }

}
