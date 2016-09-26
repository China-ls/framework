package com.infinite.eoa.service;

import com.infinite.eoa.core.serivce.IPagerService;
import com.infinite.eoa.entity.Employee;
import com.infinite.eoa.entity.EmployeeDuty;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface EmployeeService extends IPagerService<Employee> {
    public boolean isUsernameExsist(String username);

    public boolean isPhoneExsist(String phone);

    public boolean isEmailExsist(String email);

    public Employee getByUsername(String username);

    Employee addEmployee(Employee employee);

    int addEmployeeDuty(String id, EmployeeDuty duty);

    int removeEmployeeDuty(String id, String dutyid);

    int setEmployeeDuty(String id, EmployeeDuty duty);

}
