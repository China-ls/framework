package com.infinite.framework.entity;

import com.google.gson.annotations.JsonAdapter;

@JsonAdapter(PersistentRoleOperationGsonAdapter.class)
public enum PersistentRoleOperation {
    ADD("add"),
    UPDATE("update"),
    QUERY("query"),
    DELETE("delete"),;
    private String val;

    PersistentRoleOperation(String value) {
        this.val = value;
    }

    public String getValue() {
        return this.val;
    }

    public static PersistentRoleOperation fromValue(String value) {
        for (PersistentRoleOperation operation : PersistentRoleOperation.values()) {
            if (operation.getValue().equals(value)) {
                return operation;
            }
        }
        return null;
    }

}
