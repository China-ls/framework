package com.infinite.eoa.persistent;

import com.infinite.eoa.core.persistent.IMorphiaDAO;
import com.infinite.eoa.entity.DepartmentEntity;
import org.bson.types.ObjectId;

public interface DepartmentEntityDAO extends IMorphiaDAO<DepartmentEntity, ObjectId> {

    int deleteById(String id);

    int deleteByDepartmentId(String department_id);

    int deleteByEntityId(String entity_id);

}
