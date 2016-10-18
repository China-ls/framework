package com.infinite.eoa.entity.aggregation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.Document;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

@Entity
public class VsDeviceTypeAggregation {
    @Id
    private String station_type;
    @Property
    private int count;

    @JsonIgnore
    public String getStation_type() {
        return station_type;
    }

    public void setStation_type(String station_type) {
        this.station_type = station_type;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getName() {
        if (null == station_type) {
            return null;
        }
        try {
            Document document = Document.parse(station_type);
            return document.getString("station_type");
        } catch (Exception e) {
        }
        return null;
    }

}
