package com.infinite.eoa.entity;

import com.infinite.eoa.core.entity.AbstractEntity;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

@Entity(EntityConst.CollectionName.DEPARTMENT_EMPLOYEE)
public class DepartmentEntity extends AbstractEntity {
    public static enum TYPE {
        DEVICE, EMPLOYEE
    }

    @Id @Property private ObjectId id;
    @Property private ObjectId department_id;
    @Property private ObjectId entity_id;
    @Property private TYPE type;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public ObjectId getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(ObjectId department_id) {
        this.department_id = department_id;
    }

    public ObjectId getEntity_id() {
        return entity_id;
    }

    public void setEntity_id(ObjectId entity_id) {
        this.entity_id = entity_id;
    }

    public TYPE getType() {
        return type;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "DepartmentEntity{" +
                "id=" + id +
                ", department_id=" + department_id +
                ", entity_id=" + entity_id +
                ", type=" + type +
                '}';
    }
}
