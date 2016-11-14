package com.infinite.eoa.service.impl;

import com.infinite.eoa.core.entity.Pager;
import com.infinite.eoa.core.persistent.IMorphiaDAO;
import com.infinite.eoa.core.serivce.AbstractPagerService;
import com.infinite.eoa.entity.Department;
import com.infinite.eoa.entity.Employee;
import com.infinite.eoa.entity.EmployeeDuty;
import com.infinite.eoa.entity.EmployeeResourcesLevel;
import com.infinite.eoa.persistent.DepartmentDAO;
import com.infinite.eoa.persistent.EmployeeDAO;
import com.infinite.eoa.service.EmployeeService;
import com.infinite.eoa.service.exception.EntityExsistException;
import com.mongodb.WriteResult;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;

@Service("EmployeeService")
public class EmployeeServiceImpl extends AbstractPagerService<Employee> implements EmployeeService {
    @Autowired
    private EmployeeDAO employeeDAO;
    @Autowired
    private DepartmentDAO departmentDAO;

    @Override
    public Employee getEmployeeByUsername(String username) {
        return employeeDAO.findByUsername(username);
    }

    @Override
    public String getEmployeeDepartmentIdByEmployeeId(String id) {
        Employee employee = employeeDAO.findById(id);
        return null == employee ? null : employee.getDepartmentId();
    }

    @Override
    public String getEmployeeDepartmentIdByEmployeeUsername(String username) {
        Employee employee = employeeDAO.findByUsername(username);
        return null == employee ? null : employee.getDepartmentId();
    }

    @Override
    public Pager<Employee> listPager(int page, int size) {
        return listPager(page, size, employeeDAO.createQuery().filter("visable_type", 1));
    }

    @Override
    public int setEmployeeResourceDepartment(String id, String[] resDepartments) {
        Employee employee = employeeDAO.findById(id);
        if (null == employee) {
            return 0;
        }
        EmployeeResourcesLevel resLevel = employee.getResourcesLevel();
        if (resLevel == null) {
            resLevel = new EmployeeResourcesLevel();
        }
        if (null == resDepartments) {
            resLevel.setDepartments(null);
        } else {
            ArrayList<String> departments = new ArrayList<String>();
            Collections.addAll(departments, resDepartments);
            resLevel.setDepartments(departments);
        }
        employee.setResourcesLevel(resLevel);
        employeeDAO.save(employee);
        return 1;
    }

    @Override
    public IMorphiaDAO getMorphiaDAO() {
        return employeeDAO;
    }

    @Override
    public boolean isUsernameExsist(String username) {
        return employeeDAO.isUsernameExsist(username);
    }

    @Override
    public boolean isPhoneExsist(String phone) {
        return employeeDAO.isPhoneExsist(phone);
    }

    @Override
    public boolean isEmailExsist(String email) {
        return employeeDAO.isEmailExsist(email);
    }

    @Override
    public Employee getByUsername(String username) {
        return employeeDAO.findByUsername(username);
    }

    @Override
    public Employee addEmployee(Employee employee) {
        if (null == employee) {
            return null;
        }
        Employee storedEmployee = employeeDAO.findByUsername(employee.getUsername());
        if (null != storedEmployee) {
            throw new EntityExsistException(employee.getUsername());
        }
        Department department = null;

        String departmentId = employee.getDepartmentId();
        if (!StringUtils.isEmpty(departmentId)) {
            department = departmentDAO.findById(departmentId);
            employee.setDepartmentId(departmentId);
            employee.setDepartment(department);
            employee.setDepartmentName(department.getName());
        }

        employee.setVisable_type(1);
        Key<Employee> key = employeeDAO.save(employee);
        ObjectId id = (ObjectId) key.getId();
        employee.setId(id);

        if (null != department) {
            Department dptEntity = new Department();
            dptEntity.setNodeType(Department.NODE_TYPE_ENTITY);
            dptEntity.setName(employee.getName());
            dptEntity.setParentId(department.getIdHexString());
            dptEntity.setParentName(department.getName());
            dptEntity.setType(employee.getType());
            dptEntity.setLevel(department.getLevel() + 1);
            String name_path = (StringUtils.isBlank(department.getName_path()) ? "," : department.getName_path()) + department.getName() + ",";
            dptEntity.setName_path(name_path);
            String path = (StringUtils.isBlank(department.getPath()) ? "," : department.getPath()) + department.getIdHexString() + ",";
            dptEntity.setPath(path);
            dptEntity.setEntity_id(id.toHexString());

            departmentDAO.save(dptEntity);
        }

        return employee;
    }

    @Override
    public int addEmployeeDuty(String id, EmployeeDuty duty) {
        UpdateResults result = employeeDAO.update(
                employeeDAO.createQuery().filter("_id", new ObjectId(id)),
                employeeDAO.createUpdateOperations().add("duties", duty)
        );
        return result.getUpdatedCount();
    }

    @Override
    public int removeEmployeeDuty(String id, String dutyid) {
        UpdateResults result = employeeDAO.update(
                employeeDAO.createQuery().filter("_id", new ObjectId(id)),
                employeeDAO.createUpdateOperations().removeFirst("ditoes")
        );
        return result.getUpdatedCount();
    }

    @Override
    public int setEmployeeDuty(String id, EmployeeDuty duty) {
        UpdateResults result = employeeDAO.update(
                employeeDAO.createQuery().filter("_id", new ObjectId(id)),
                employeeDAO.createUpdateOperations().set("duties", duty)
        );
        return result.getUpdatedCount();
    }

    @Override
    public int removeEmployee(String id) {
        departmentDAO.deleteByEntityId(id);
        WriteResult result = employeeDAO.deleteById(new ObjectId(id));
        return result.getN();
    }
}
