package com.infinite.eoa.router.entity;

import com.infinite.eoa.entity.Department;
import org.apache.commons.lang.StringUtils;

public class DepartmentModel extends Department {
    private String p_id;
    private String p_name;
    private String p_path;
    private int p_level;

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

    public int getP_level() {
        return p_level;
    }

    public void setP_level(int p_level) {
        this.p_level = p_level;
    }

    public Department convertAdd() {
        Department department = new Department();
        department.setName(getName());
        department.setType(getType());
        department.setLeading(getLeading());
        department.setAddress(getAddress());
        department.setParentName(p_name);
        department.setLevel(p_level + 1);
        if (!StringUtils.isEmpty(p_id)) {
            department.setParentId(p_id);
            p_path = StringUtils.isEmpty(p_path) ? "," : p_path;
            department.setPath(p_path + p_id + ",");
        }
        return department;
    }

    @Override
    public String toString() {
        return "DepartmentModel{" +
                "p_id='" + p_id + '\'' +
                ", p_name='" + p_name + '\'' +
                ", p_path='" + p_path + '\'' +
                ", p_level=" + p_level +
                "} " + super.toString();
    }
}
