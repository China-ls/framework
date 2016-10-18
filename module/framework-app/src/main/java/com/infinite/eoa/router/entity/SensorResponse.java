package com.infinite.eoa.router.entity;

import com.infinite.eoa.core.entity.AbstractEntity;
import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.entity.VirtualSensorData;

import java.util.ArrayList;

/**
 * @author by hx on 16-7-6.
 */
public class SensorResponse extends AbstractEntity {
    private VirtualSensor sensor;
    private ArrayList<VirtualSensorData> data;

    public VirtualSensor getSensor() {
        return sensor;
    }

    public void setSensor(VirtualSensor sensor) {
        this.sensor = sensor;
    }

    public ArrayList<VirtualSensorData> getData() {
        return data;
    }

    public void setData(ArrayList<VirtualSensorData> data) {
        this.data = data;
    }

}