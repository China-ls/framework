package com.infinite.eoa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infinite.eoa.core.entity.AbstractEntity;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

import java.util.Date;

@Entity(value = EntityConst.CollectionName.EMPLOYEE, noClassnameStored = true)
public class Employee extends AbstractEntity {
    /**
     * 巡线员
     */
    public static final int TYPE_LINE_PATROL = 1;
    /**
     * 工程人员
     */
    public static final int TYPE_ENGINEERING = 2;
    /**
     * 核查人员
     */
    public static final int TYPE_VERIFICATION = 3;

    public static final int TYPE_NORMAL = 5;


    @Id @Property private ObjectId id;
    //员工工号
    @Property private String number;
    @Property private String username;
    @Property private String password;
    @Property private String name;
    //邮政编码
    @Property private String postalcode;
    @Property private int type;
    @Property private int visable_type;
    @Property private int sex;
    //状态 1:正常 2:禁用 9:删除
    @Property private int status;
    @Property private String tel;
    @Property private String email;
    //区域归属 ID
    @Property private String departmentId;
    //区域归属 名字
    @Property private String departmentName;
    @Property private Date birthday;
    @Property private String phone;
    @Property private String address;
    @Property private int sort;
    @Embedded private EmployeeDuty duty;
    @Reference private Department department;
    @Embedded private EmployeeResourcesLevel resourcesLevel;

    public Employee() {
    }

    public Employee(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @JsonIgnore
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public void setVisable_type(int visable_type) {
        this.visable_type = visable_type;
    }

    public int getVisable_type() {
        return visable_type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public EmployeeDuty getDuty() {
        return duty;
    }

    public void setDuty(EmployeeDuty duty) {
        this.duty = duty;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public EmployeeResourcesLevel getResourcesLevel() {
        return resourcesLevel;
    }

    public void setResourcesLevel(EmployeeResourcesLevel resourcesLevel) {
        this.resourcesLevel = resourcesLevel;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", name='" + name + '\'' +
                ", postalcode='" + postalcode + '\'' +
                ", type=" + type +
                ", visable_type=" + visable_type +
                ", sex=" + sex +
                ", status=" + status +
                ", tel='" + tel + '\'' +
                ", email='" + email + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", departmentName='" + departmentName + '\'' +
                ", birthday=" + birthday +
                ", phone='" + phone + '\'' +
                ", address='" + address + '\'' +
                ", sort=" + sort +
                ", duty=" + duty +
                ", department=" + department +
                ", resourcesLevel=" + resourcesLevel +
                '}';
    }
}