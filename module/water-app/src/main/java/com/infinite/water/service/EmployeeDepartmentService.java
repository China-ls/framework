package com.infinite.water.service;

import com.infinite.water.entity.Employee;
import com.infinite.water.entity.EmployeeDepartment;
import org.bson.Document;

import java.io.IOException;
import java.util.List;

public interface EmployeeDepartmentService {

    public List<Document> getAllDepartment() throws IOException;

    public List<EmployeeDepartment> getDepartmentTreeRoot() throws IOException;

    public Object addDepartment(EmployeeDepartment department) throws IOException;

    public Object updateDepartment(EmployeeDepartment department) throws IOException;

    public Object deleteDepartmentById(String id) throws IOException;

    public Document addEmployee(EmployeeDepartment department, Employee employee) throws IOException;

    public Object deleteEmployeeById(String id) throws IOException;

}
