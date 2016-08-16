package com.infinite.water.service;

import com.infinite.water.entity.VirtualSensor;

import java.util.List;

public interface VirtualSensorService {
    public List<VirtualSensor> getAllVirtualSensor();

    public VirtualSensor getVirtualSensor(String id);
}
