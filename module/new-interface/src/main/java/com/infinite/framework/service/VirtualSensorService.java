package com.infinite.framework.service;

import com.infinite.framework.entity.VirtualSensor;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface VirtualSensorService {

    public VirtualSensor createVirtualSensor(String appkey, VirtualSensor sensor);

    public VirtualSensor updateVirtualSensor(VirtualSensor sensor);

    public VirtualSensor remveVirtualSensor(String sensorId);

    VirtualSensor findById(String id);
}
