package com.infinite.eoa.service;

import com.infinite.eoa.core.entity.Pager;
import com.infinite.eoa.core.serivce.IPagerService;
import com.infinite.eoa.entity.Department;
import org.bson.Document;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface DepartmentService extends IPagerService<Department> {

    public int deleteById(String id);

    public Document listByType(String type);

    public Pager<Department> listPagerAll(int page, int size, boolean withEntity);

    public Pager<Department> listPagerByDepartmentType(int page, int size, int type, boolean withEntity);

    Department save(Department department);
}
