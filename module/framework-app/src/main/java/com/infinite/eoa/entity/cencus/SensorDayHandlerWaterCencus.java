package com.infinite.eoa.entity.cencus;

import com.infinite.eoa.entity.EntityConst;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

@Entity(value = EntityConst.CollectionName.CENCUS_COMPONENT_DAY_WATER_HANDLER, noClassnameStored = true)
public class SensorDayHandlerWaterCencus extends DayCencusEntity {
    @Property
    private String comp_id;
    @Property
    private double value;

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getComp_id() {
        return comp_id;
    }

    public void setComp_id(String comp_id) {
        this.comp_id = comp_id;
    }

    @Override
    public String toString() {
        return "SensorDayHandlerWaterCencus{" +
                ", sensor_id='" + sensor_id + '\'' +
                ", comp_id='" + comp_id + '\'' +
                ", value=" + value +
                '}';
    }
}
