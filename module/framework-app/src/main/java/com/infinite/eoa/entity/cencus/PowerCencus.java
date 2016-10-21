package com.infinite.eoa.entity.cencus;

import com.infinite.eoa.entity.EntityConst;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

@Entity(value = EntityConst.CollectionName.CENCUS_POWER, noClassnameStored = true)
public class PowerCencus extends DayCencusEntity {
    @Property
    private double power;

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    @Override
    public String toString() {
        return "PowerCencus{" +
                "power=" + power +
                "} " + super.toString();
    }
}