package com.infinite.eoa.entity.cencus;

import com.infinite.eoa.core.entity.AbstractEntity;
import org.mongodb.morphia.annotations.Property;

public class CencusEntity extends AbstractEntity {
    @Property private String sensor_id;

    public String getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(String sensor_id) {
        this.sensor_id = sensor_id;
    }

    @Override
    public String toString() {
        return "CencusEntity{" +
                "sensor_id='" + sensor_id + '\'' +
                '}';
    }
}
