package com.infinite.eoa.entity.cencus;

import com.infinite.eoa.core.entity.AbstractEntity;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

public class CencusEntity extends AbstractEntity {
    @Id private ObjectId id;
    @Property private String sensor_id;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(String sensor_id) {
        this.sensor_id = sensor_id;
    }

    @Override
    public String toString() {
        return "CencusEntity{" +
                "id=" + id +
                ", sensor_id='" + sensor_id + '\'' +
                "} " + super.toString();
    }
}
