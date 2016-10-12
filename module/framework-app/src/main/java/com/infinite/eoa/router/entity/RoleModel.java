package com.infinite.eoa.router.entity;

import com.infinite.eoa.entity.Role;
import org.apache.commons.lang.StringUtils;

public class RoleModel extends Role {
    private String p_id;
    private String p_path;
    private String p_name;
    private String p_name_path;
    private int p_level;
    private String pks;

    public String getP_id() {
        return p_id;
    }

    public void setP_id(String p_id) {
        this.p_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public String getP_path() {
        return p_path;
    }

    public void setP_path(String p_path) {
        this.p_path = p_path;
    }

    public String getP_name_path() {
        return p_name_path;
    }

    public void setP_name_path(String p_name_path) {
        this.p_name_path = p_name_path;
    }

    public int getP_level() {
        return p_level;
    }

    public void setP_level(int p_level) {
        this.p_level = p_level;
    }

    public String getPks() {
        return pks;
    }

    public void setPks(String pks) {
        this.pks = pks;
    }

    public Role convertAdd() {
        Role role = new Role();
        role.setName(getName());
        role.setDescription(getDescription());
        role.setParentName(p_name);
        role.setLevel(p_level + 1);
        role.setStatus(getStatus());
        if (!StringUtils.isEmpty(p_id)) {
            role.setParentId(p_id);
            p_path = StringUtils.isEmpty(p_path) ? "," : p_path;
            role.setPath(p_path + p_id + ",");
        }
        if (!StringUtils.isEmpty(p_name)) {
            role.setParentName(p_name);
            p_name_path = StringUtils.isEmpty(p_name_path) ? "," : p_name_path;
            role.setNamePath(p_name_path + p_name + ",");
        }
        return role;
    }

    @Override
    public String toString() {
        return "RoleModel{" +
                "p_id='" + p_id + '\'' +
                ", p_path='" + p_path + '\'' +
                ", p_name='" + p_name + '\'' +
                ", p_name_path='" + p_name_path + '\'' +
                "} " + super.toString();
    }
}
