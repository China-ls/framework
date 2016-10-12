package com.infinite.eoa.persistent;

import com.infinite.eoa.core.persistent.IMorphiaDAO;
import com.infinite.eoa.entity.Permission;
import org.bson.types.ObjectId;

import java.util.List;

public interface PermissionDAO extends IMorphiaDAO<Permission, ObjectId> {

    public Permission findByname(String name);

    List<Permission> findByIdArray(String[] permissionIds);
}
