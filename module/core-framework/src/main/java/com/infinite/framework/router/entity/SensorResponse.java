package com.infinite.framework.router.entity;

import com.infinite.framework.core.entity.AbstractEntity;
import com.infinite.framework.entity.VirtualSensor;
import org.bson.Document;

import java.util.List;

/**
 * @author by hx on 16-7-6.
 */
public class SensorResponse extends AbstractEntity {
    private VirtualSensor sensor;
    private List<Document> data;

    public VirtualSensor getSensor() {
        return sensor;
    }

    public void setSensor(VirtualSensor sensor) {
        this.sensor = sensor;
    }

    public List<Document> getData() {
        return data;
    }

    public void setData(List<Document> data) {
        this.data = data;
    }
}