package com.infinite.eoa.persistent;

import com.infinite.eoa.core.persistent.IMorphiaDAO;
import com.infinite.eoa.entity.Department;
import org.bson.types.ObjectId;

public interface DepartmentDAO extends IMorphiaDAO<Department, ObjectId> {

    public Department findById(String id);

}
