package com.infinite.framework.entity;

import com.google.gson.annotations.SerializedName;
import com.infinite.framework.core.util.JsonUtil;

public class PersistentRole {
    @SerializedName("_id")
    private String id;
    private String name;
    private PersistentRoleOperation operation;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public PersistentRoleOperation getOperation() {
        return operation;
    }

    public void setOperation(PersistentRoleOperation operation) {
        this.operation = operation;
    }

    @Override
    public String toString() {
        return "PersistentRole{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", operation=" + operation +
                '}';
    }

    public static void main(String[] args) {
        PersistentRole role = new PersistentRole();
        role.name = "name";
        role.operation = PersistentRoleOperation.DELETE;
        System.out.println(JsonUtil.toJson(role));
    }

}
