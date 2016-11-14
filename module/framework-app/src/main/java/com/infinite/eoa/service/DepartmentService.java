package com.infinite.eoa.service;

import com.infinite.eoa.core.entity.Pager;
import com.infinite.eoa.core.serivce.IPagerService;
import com.infinite.eoa.entity.Department;
import com.infinite.eoa.entity.Employee;
import org.bson.Document;

import java.util.List;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface DepartmentService extends IPagerService<Department> {

    public int deleteById(String id);

    public Document listByType(String type);

    public Department listByDepartentType(int type);

    public Pager<Department> listPagerAll(int page, int size, int withEntity);

    public Pager<Department> listPagerByDepartmentType(Employee employee, int page, int size, Iterable<Integer> type, int withEntity);

    Department save(Department department);

    Department findById(String departmentId);

    int deleteByEntityId(String sensorId);

    Department findByEntity_id(String entity_id);

    int delete(Department dptEntity);

    List<Department> findEntityIdsByTypeAndDepartmentId(List<Integer> type, String departmentId);
}
