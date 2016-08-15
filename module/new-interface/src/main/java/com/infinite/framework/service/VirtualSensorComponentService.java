package com.infinite.framework.service;

import com.infinite.framework.entity.Action;
import com.infinite.framework.entity.Component;
import com.infinite.framework.entity.VirtualSensor;
import org.bson.types.ObjectId;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface VirtualSensorComponentService {

    public VirtualSensor addComponent(String sensorId, Component component);

    public VirtualSensor addComponent(VirtualSensor sensor, Component component);

    public VirtualSensor updateComponent(Component component);

    public VirtualSensor remveComponent(ObjectId componentId);

    public VirtualSensor addComponentAction(String componentId, Action action);

}
