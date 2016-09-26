package com.infinite.eoa.persistent;

import com.infinite.eoa.core.persistent.IMorphiaDAO;
import com.infinite.eoa.entity.Employee;
import org.bson.types.ObjectId;
import org.mongodb.morphia.query.UpdateResults;

public interface EmployeeDAO extends IMorphiaDAO<Employee, ObjectId> {

    public Employee findById(String id);

    public Employee findByUsername(String username);

    public boolean isUsernameExsist(String username);

    public boolean isPhoneExsist(String phone);

    public boolean isEmailExsist(String email);

    public UpdateResults updatePasswordByUsername(String username, String password);

    public UpdateResults updatePasswordById(String id, String password);

    public UpdateResults updateName(String id, String name);

}
