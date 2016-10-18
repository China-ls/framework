package com.infinite.eoa.entity;

import com.infinite.eoa.core.entity.AbstractEntity;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

import java.util.ArrayList;
import java.util.List;

@Entity(value = EntityConst.CollectionName.ROLE, noClassnameStored = true)
public class Role extends AbstractEntity {
    @Id
    @Property
    private ObjectId id;
    @Property
    private String name;
    @Property
    private String description;
    @Property
    private String parentName;
    @Property
    private String parentId;
    @Property
    private String namePath;
    @Property
    private String path;
    //状态 1:正常 2:禁用 9:删除
    private int status;
    @Property
    private int level;
    @Reference
    private ArrayList<Permission> permissions = new ArrayList<Permission>(0);

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getNamePath() {
        return namePath;
    }

    public void setNamePath(String namePath) {
        this.namePath = namePath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public ArrayList<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<Permission> permissions) {
        this.permissions.clear();
        if (null != permissions) {
            this.permissions.addAll(permissions);
        }
    }

    @Override
    public String toString() {
        return "Role{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", parentName='" + parentName + '\'' +
                ", parentId='" + parentId + '\'' +
                ", namePath='" + namePath + '\'' +
                ", path='" + path + '\'' +
                ", status=" + status +
                ", level=" + level +
                ", permissions=" + permissions +
                '}';
    }
}