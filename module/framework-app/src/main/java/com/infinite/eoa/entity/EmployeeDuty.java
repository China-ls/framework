package com.infinite.eoa.entity;

import com.infinite.eoa.core.entity.AbstractEntity;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

//@Entity(EntityConst.CollectionName.EMPLOYEE_DUTY)
public class EmployeeDuty extends AbstractEntity {
    /**
     * 巡线
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

    @Id
    @Property
    private ObjectId id;
    @Property
    private int type;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "EmployeeDuty{" +
                "id=" + id +
                ", type=" + type +
                "} " + super.toString();
    }
}