package com.infinite.eoa.entity.aggregation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.bson.Document;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

@Entity
public class VsFieldMinMaxAggregation {
    @Id
    private String sensor_id;
    @Property
    private double max;
    @Property
    private double min;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void convertId() {
        if (null == sensor_id) {
            return;
        }
        try {
            this.id = Document.parse(sensor_id).getString("sensor_id");
        } catch (Exception e) {
        }
    }

    @JsonIgnore
    public String getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(String sensor_id) {
        this.sensor_id = sensor_id;
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
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    public Double minus() {
        return this.max - this.min;
    }
}
