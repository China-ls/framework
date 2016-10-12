package com.infinite.eoa.service.impl;

import com.infinite.eoa.entity.Permission;
import com.infinite.eoa.entity.Role;
import com.infinite.eoa.persistent.PermissionDAO;
import com.infinite.eoa.persistent.RoleDAO;
import com.infinite.eoa.service.RoleService;
import com.mongodb.WriteResult;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Key;
import org.mongodb.morphia.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("RoleService")
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDAO roleDAO;
    @Autowired
    private PermissionDAO permissionDAO;

    @Override
    public Role findByName(String name) {
        return roleDAO.findOne("name", name);
    }

    @Override
    public Role save(Role role) {
        Key<Role> key = roleDAO.save(role);
        role.setId((ObjectId) key.getId());
        return role;
    }

    @Override
    public Role addRole(Role role, String[] permissionIds) {
        List<Permission> permissions = permissionDAO.findByIdArray(permissionIds);
        role.setPermissions(permissions);
        Key<Role> key = roleDAO.save(role);
        role.setId((ObjectId) key.getId());
        return role;
    }

    @Override
    public Role updateRole(Role role, String[] permissionIds) {
        if (null == role.getId()) {
            return null;
        }
        return addRole(role, permissionIds);
    }

    @Override
    public int removeRole(String id) {
        Query<Role> query = roleDAO.createQuery();
        query.or(roleDAO.createQuery().criteria("id").equal(new ObjectId(id)),
                roleDAO.createQuery().criteria("path").contains(id));
        WriteResult result = roleDAO.deleteByQuery(query);
        return result.getN();
    }

    @Override
    public List<Role> listAll() {
        List<Role> list = roleDAO.find(roleDAO.createQuery().order("path")).asList();
        if (null != list && list.size() > 1) {
            int size = list.size();

            String path = null;
            String childpath = null;
            String id = null;
            int index = -1;
            for (int i = 0; i < size; i++) {
                Role item = list.get(i);
                path = item.getPath();
                if (path == null) {
                    continue;
                }
                id = item.getId().toHexString();
                childpath = path + id + ",";
                index = i;
                for (int j = index; j < size; j++) {
                    Role child = list.get(j);
                    if (childpath.equals(child.getPath())) {
                        if (j - index > 1) {
                            index++;
                            Role willmove = list.remove(j);
                            list.add(index, willmove);
                        }
                    }
                }
            }
        }
        return list;
    }

    @Override
    public List<String> getPermissionKeyListByRoleId(String roleid) {
        if (StringUtils.isEmpty(roleid)) {
            return null;
        }
        ArrayList<String> keys = new ArrayList<String>();
        Role role = roleDAO.findById(roleid);
        if (null == role) {
            return null;
        }
        ArrayList<Permission> permissions = role.getPermissions();
        for (Permission permission : permissions) {
            keys.add(permission.getId().toHexString());
        }
        return keys;
    }
}
