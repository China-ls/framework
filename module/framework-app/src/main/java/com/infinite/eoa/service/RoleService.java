package com.infinite.eoa.service;

import com.infinite.eoa.entity.Role;

import java.util.List;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface RoleService {

    public Role findByName(String name);

    public Role save(Role role);

    public Role addRole(Role role, String[] permissionIds);

    public Role updateRole(Role role, String[] permissionIds);

    public int removeRole(String id);

    List<Role> listAll();

    List<String> getPermissionKeyListByRoleId(String roleid);
}
