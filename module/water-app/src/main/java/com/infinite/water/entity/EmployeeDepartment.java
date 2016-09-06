package com.infinite.water.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;

public class EmployeeDepartment extends BasicEntity {
    public static String TYPE_DEPARTMENT = "department";
    public static String TYPE_EMPLOYEE = "employee";

    @SerializedName("_id")
    private ObjectId id;
    private String name;
    private String address;
    private String contact;
    private String parent_id;
    private String employee_id;
    private String type;
    private EmployeeDepartment parent;
    private String path;
    private int sort;
    private int level;

    public ObjectId getId() {
        return id;
    }

    @JsonIgnore
    public String getIdHexString() {
        return id.toHexString();
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getParent_id() {
        return parent_id;
    }

    public void setParent_id(String parent_id) {
        this.parent_id = parent_id;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public EmployeeDepartment getParent() {
        return parent;
    }

    public void setParent(EmployeeDepartment parent) {
        this.parent = parent;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(String employee_id) {
        this.employee_id = employee_id;
    }

    @Override
    public String toString() {
        return "EmployeeDepartment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", contact='" + contact + '\'' +
                ", parent_id='" + parent_id + '\'' +
                ", type='" + type + '\'' +
                ", parent=" + parent +
                ", path='" + path + '\'' +
                ", sort=" + sort +
                ", level=" + level +
                '}';
    }
}