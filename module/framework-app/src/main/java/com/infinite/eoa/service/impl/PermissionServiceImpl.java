package com.infinite.eoa.service.impl;

import com.infinite.eoa.entity.Permission;
import com.infinite.eoa.persistent.PermissionDAO;
import com.infinite.eoa.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("PermissionService")
public class PermissionServiceImpl implements PermissionService {

    @Autowired
    private PermissionDAO permissionDAO;

    @Override
    public List<Permission> findAll() {
        return permissionDAO.find().asList();
    }
}
