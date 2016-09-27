package com.infinite.eoa.service.impl;

import com.infinite.eoa.core.persistent.IMorphiaDAO;
import com.infinite.eoa.core.serivce.AbstractPagerService;
import com.infinite.eoa.entity.Employee;
import com.infinite.eoa.entity.EmployeeDuty;
import com.infinite.eoa.persistent.EmployeeDAO;
import com.infinite.eoa.service.EmployeeService;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("EmployeeService")
public class EmployeeServiceImpl extends AbstractPagerService<Employee> implements EmployeeService {
    @Autowired
    private EmployeeDAO employeeDAO;

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
        Key<Employee> key = employeeDAO.save(employee);
        employee.setId((ObjectId) key.getId());
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
}