package com.infinite.eoa.entity.aggregation;

import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

public class SensorMaxMinAggregation {
    @Id private SensorComponentId id;
    @Property private double max;
    @Property private double min;

    public SensorComponentId getId() {
        return id;
    }

    public void setId(SensorComponentId id) {
        this.id = id;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    @Override
    public String toString() {
        return "SensorMaxMinAggregation{" +
                "id='" + id + '\'' +
                ", max=" + max +
                ", min=" + min +
                '}';
    }
}
