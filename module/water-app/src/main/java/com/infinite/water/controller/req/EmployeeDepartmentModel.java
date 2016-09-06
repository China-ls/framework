package com.infinite.water.controller.req;

import com.infinite.water.entity.BasicEntity;
import com.infinite.water.entity.EmployeeDepartment;
import org.apache.commons.lang.StringUtils;
import org.bson.types.ObjectId;

public class EmployeeDepartmentModel extends BasicEntity {
    private ObjectId id;
    private String name;
    private String contact;
    private String address;
    private String pid;
    private String ppath;
    private int pl;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPid() {
        return pid;
    }

    public void setPid(String pid) {
        this.pid = pid;
    }

    public String getPpath() {
        return ppath;
    }

    public void setPpath(String ppath) {
        this.ppath = ppath;
    }

    public int getPl() {
        return pl;
    }

    public void setPl(int pl) {
        this.pl = pl;
    }

    public EmployeeDepartment convertToEmployeeDepartment() {
        EmployeeDepartment department = new EmployeeDepartment();
        department.setId(id);
        department.setName(name);
        department.setAddress(address);
        department.setContact(contact);
        if (!StringUtils.isEmpty(pid)) {
            department.setParent_id(pid);
            ppath = StringUtils.isEmpty(ppath) ? "," : ppath;
            department.setPath(ppath + pid + ",");
        }
        department.setLevel(pl + 1);
        return department;
    }

    @Override
    public String toString() {
        return "EmployeeDepartmentModel{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", contact='" + contact + '\'' +
                ", address='" + address + '\'' +
                ", pid='" + pid + '\'' +
                ", ppath='" + ppath + '\'' +
                ", pl=" + pl +
                '}';
    }
}
