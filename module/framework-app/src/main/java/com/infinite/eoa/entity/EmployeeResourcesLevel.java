package com.infinite.eoa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infinite.eoa.core.entity.AbstractEntity;

import java.util.ArrayList;

public class EmployeeResourcesLevel extends AbstractEntity {
    public static final int LEVE_NORMAL = 0;
    public static final int LEVE_ADMIN = 1;
    public static final int LEVE_SUPPER_ADMIN = 2;
    public static final int LEVE_INFINITE = 999;

    private int level;

    private ArrayList<String> departments = new ArrayList<String>();

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<String> getDepartments() {
        return departments;
    }

    public void setDepartments(ArrayList<String> departments) {
        this.departments = departments;
    }

    @JsonIgnore
    public boolean isAdmin() {
        return LEVE_ADMIN <= level;
    }

    @JsonIgnore
    public boolean isSupperAdmin() {
        return LEVE_SUPPER_ADMIN <= level;
    }

    @JsonIgnore
    public boolean awesome() {
        return LEVE_INFINITE <= level;
    }
}