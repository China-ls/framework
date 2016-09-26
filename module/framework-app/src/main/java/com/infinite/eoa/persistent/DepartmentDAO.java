package com.infinite.eoa.persistent;

import com.infinite.eoa.core.persistent.IMorphiaDAO;
import com.infinite.eoa.entity.Department;

public interface DepartmentDAO extends IMorphiaDAO<Department, String> {

    public Department findById(String id);

}
