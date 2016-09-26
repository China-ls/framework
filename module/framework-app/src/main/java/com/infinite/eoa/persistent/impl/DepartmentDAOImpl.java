package com.infinite.eoa.persistent.impl;

import com.infinite.eoa.core.persistent.MorphiaDAO;
import com.infinite.eoa.entity.Department;
import com.infinite.eoa.persistent.DepartmentDAO;
import org.mongodb.morphia.Datastore;

public class DepartmentDAOImpl extends MorphiaDAO<Department, String> implements DepartmentDAO {
    public DepartmentDAOImpl(Datastore ds) {
        super(ds);
    }

    @Override
    public Department findById(String id) {
        return findOne("id", id);
    }

}
