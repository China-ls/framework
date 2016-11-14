package com.infinite.eoa.entity.aggregation;

import org.mongodb.morphia.annotations.Property;

public class SensorComponentId {
    @Property private String sensor_id;
    @Property private String comp_id;

    public String getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(String sensor_id) {
        this.sensor_id = sensor_id;
    }

    public String getComp_id() {
        return comp_id;
    }

    public void setComp_id(String comp_id) {
        this.comp_id = comp_id;
    }

    @Override
    public String toString() {
        return "SensorComponentId{" +
                "sensor_id='" + sensor_id + '\'' +
                ", comp_id='" + comp_id + '\'' +
                '}';
    }
}
