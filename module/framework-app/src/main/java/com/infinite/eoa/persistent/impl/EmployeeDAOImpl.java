package com.infinite.eoa.persistent.impl;

import com.infinite.eoa.core.persistent.MorphiaDAO;
import com.infinite.eoa.entity.Employee;
import com.infinite.eoa.persistent.EmployeeDAO;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.UpdateResults;

public class EmployeeDAOImpl extends MorphiaDAO<Employee, ObjectId> implements EmployeeDAO {
    public EmployeeDAOImpl(Datastore ds) {
        super(ds);
    }

    @Override
    public Employee findById(String id) {
        return findOne("id", new ObjectId(id));
    }

    @Override
    public Employee findByUsername(String username) {
        return findOne("username", username);
    }

    @Override
    public boolean isUsernameExsist(String username) {
        return exists("username", username);
    }

    @Override
    public boolean isEmailExsist(String email) {
        return exists("email", email);
    }

    @Override
    public boolean isPhoneExsist(String phone) {
        return exists("phone", phone);
    }

    @Override
    public UpdateResults updatePasswordByUsername(String username, String password) {
        return update(
                createQuery().filter("username", username),
                createUpdateOperations().set("password", password)
        );
    }

    @Override
    public UpdateResults updatePasswordById(String id, String password) {
        return update(
                createQuery().filter("id", id),
                createUpdateOperations().set("password", password)
        );
    }

    @Override
    public UpdateResults updateName(String id, String name) {
        return update(
                createQuery().filter("id", id),
                createUpdateOperations().set("name", name)
        );
    }
}
