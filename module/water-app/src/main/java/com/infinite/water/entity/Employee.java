package com.infinite.water.entity;

import com.google.gson.annotations.SerializedName;
import org.bson.types.ObjectId;

import java.sql.Timestamp;

public class Employee extends BasicEntity {
    @SerializedName("_id")
    private ObjectId id;
    private String name;
    private String type;
    private String sex;
    private String departmentId;
    private Timestamp born;
    private String contact;
    private String address;
    private String identityNumber;
    private int sort;

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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Timestamp getBorn() {
        return born;
    }

    public void setBorn(Timestamp born) {
        this.born = born;
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

    public String getIdentityNumber() {
        return identityNumber;
    }

    public void setIdentityNumber(String identityNumber) {
        this.identityNumber = identityNumber;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", sex='" + sex + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", born=" + born +
                ", contact='" + contact + '\'' +
                ", address='" + address + '\'' +
                ", identityNumber='" + identityNumber + '\'' +
                ", sort=" + sort +
                '}';
    }
}