package com.infinite.eoa.persistent;

import com.infinite.eoa.core.persistent.IMorphiaDAO;
import com.infinite.eoa.entity.Role;
import org.bson.types.ObjectId;

public interface RoleDAO extends IMorphiaDAO<Role, ObjectId> {

    public Role findByname(String name);

    Role findById(String id);
}
