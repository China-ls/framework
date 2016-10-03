package com.infinite.eoa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.gson.annotations.SerializedName;
import com.infinite.eoa.core.entity.AbstractEntity;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

@Entity(EntityConst.CollectionName.DEPARTMENT)
public class Department extends AbstractEntity {
    /**
     * 节点是组织机构
     */
    public static final int NODE_TYPE_DEFAULT = 0;
    /**
     * 节点是对象
     */
    public static final int NODE_TYPE_ENTITY = 1;


    @SerializedName("_id")
    @Id @Property private ObjectId id;
    @Property private String name;
    @Property private String address;
    @Property private String leading;
    @Property private String parentId;
    @Property private String parentName;
    @Property private int type;
    @Property private String path;
    @Property private String name_path;
    @Property private int nodeType;
    @Property private int level;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    @JsonIgnore
    public String getIdHexString() {
        return null == id ? null : id.toHexString();
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

    public String getLeading() {
        return leading;
    }

    public void setLeading(String leading) {
        this.leading = leading;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getNodeType() {
        return nodeType;
    }

    public void setNodeType(int nodeType) {
        this.nodeType = nodeType;
    }

    public String getName_path() {
        return name_path;
    }

    public void setName_path(String name_path) {
        this.name_path = name_path;
    }

    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", leading='" + leading + '\'' +
                ", parentId='" + parentId + '\'' +
                ", parentName='" + parentName + '\'' +
                ", type=" + type +
                ", path='" + path + '\'' +
                ", name_path='" + name_path + '\'' +
                ", nodeType=" + nodeType +
                ", level=" + level +
                '}';
    }
}
