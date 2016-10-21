package com.infinite.eoa.entity.cencus;

import com.infinite.eoa.entity.EntityConst;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

@Entity(value = EntityConst.CollectionName.CENCUS_POSITIVE_TOTAL, noClassnameStored = true)
public class PositiveTotalCencus extends DayCencusEntity {
    @Property
    private String positive_total;

    public String getPositive_total() {
        return positive_total;
    }

    public void setPositive_total(String positive_total) {
        this.positive_total = positive_total;
    }

    @Override
    public String toString() {
        return "PositiveTotalCencus{" +
                "positive_total='" + positive_total + '\'' +
                "} " + super.toString();
    }
}
