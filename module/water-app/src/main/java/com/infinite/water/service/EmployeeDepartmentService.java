package com.infinite.water.service;

import com.infinite.water.entity.Employee;
import com.infinite.water.entity.EmployeeDepartment;
import org.bson.Document;

import java.io.IOException;
import java.util.List;

public interface EmployeeDepartmentService {

    public List<Document> getAll() throws IOException;

    public List<EmployeeDepartment> getTreeRoot() throws IOException;

    public Object add(EmployeeDepartment department) throws IOException;

    public Object update(EmployeeDepartment department) throws IOException;

    public Object deleteById(String id) throws IOException;

    public Object addEmployee(EmployeeDepartment department, Employee employee) throws IOException;

}
