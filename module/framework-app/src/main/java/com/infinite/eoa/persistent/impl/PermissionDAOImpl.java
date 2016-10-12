package com.infinite.eoa.persistent.impl;

import com.infinite.eoa.core.persistent.MorphiaDAO;
import com.infinite.eoa.entity.Permission;
import com.infinite.eoa.persistent.PermissionDAO;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

import java.util.ArrayList;
import java.util.List;

public class PermissionDAOImpl extends MorphiaDAO<Permission, ObjectId> implements PermissionDAO {
    public PermissionDAOImpl(Datastore ds) {
        super(ds);
    }

    @Override
    public Permission findByname(String name) {
        return findOne("name", name);
    }

    @Override
    public List<Permission> findByIdArray(String[] permissionIds) {
        if (null == permissionIds) {
            return null;
        }
        ArrayList<ObjectId> objectIds = new ArrayList<ObjectId>();
        for (String id : permissionIds) {
            objectIds.add(new ObjectId(id));
        }
        return find(createQuery().field("id").in(objectIds)).asList();
    }
}
