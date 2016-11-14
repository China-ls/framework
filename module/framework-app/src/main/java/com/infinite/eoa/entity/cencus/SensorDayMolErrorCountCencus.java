package com.infinite.eoa.entity.cencus;

import com.infinite.eoa.entity.EntityConst;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

@Entity(value = EntityConst.CollectionName.CENCUS_COMPONENT_DAY_MOL_ERROR_COUNT, noClassnameStored = true)
public class SensorDayMolErrorCountCencus extends DayCencusEntity {
    @Property private String comp_id;
    @Property private String comp_type;
    @Property private String comp_name;
    @Property private int instance_type;
    @Property private int value;

    public String getComp_id() {
        return comp_id;
    }

    public void setComp_id(String comp_id) {
        this.comp_id = comp_id;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getComp_type() {
        return comp_type;
    }

    public void setComp_type(String comp_type) {
        this.comp_type = comp_type;
    }

    public String getComp_name() {
        return comp_name;
    }

    public void setComp_name(String comp_name) {
        this.comp_name = comp_name;
    }

    public int getInstance_type() {
        return instance_type;
    }

    public void setInstance_type(int instance_type) {
        this.instance_type = instance_type;
    }

    @Override
    public String toString() {
        return "SensorDayMolErrorCountCencus{" +
                "comp_id='" + comp_id + '\'' +
                ", comp_type='" + comp_type + '\'' +
                ", comp_name='" + comp_name + '\'' +
                ", instance_type=" + instance_type +
                ", value=" + value +
                '}';
    }
}
