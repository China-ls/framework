package com.infinite.eoa.entity.aggregation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.bson.Document;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

@Entity
public class VsDeviceStatusAggregation {
    @Id
    private String online_text;
    @Property
    private int count;

    @JsonIgnore
    public String getOnline_text() {
        return online_text;
    }

    public void setOnline_text(String online_text) {
        this.online_text = online_text;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getOnline() {
        if (null == online_text) {
            return 0;
        }
        try {
            Document document = Document.parse(online_text);
            return document.getInteger("online");
        } catch (Exception e) {
        }
        return 0;
    }
}
