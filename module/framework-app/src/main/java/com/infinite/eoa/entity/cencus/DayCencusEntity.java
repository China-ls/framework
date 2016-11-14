package com.infinite.eoa.entity.cencus;

import org.mongodb.morphia.annotations.Property;

import java.util.Date;

public class DayCencusEntity extends CencusEntity {
    @Property protected Date time;

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "DayCencusEntity{" +
                "time=" + time +
                "} " + super.toString();
    }
}
