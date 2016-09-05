package com.infinite.framework.service;

import com.infinite.framework.entity.VirtualSensor;
import com.infinite.framework.service.exception.ApplicationNotExsistException;

import java.util.List;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface VirtualSensorService {

    public VirtualSensor createVirtualSensor(String appkey, VirtualSensor sensor);

    public VirtualSensor updateVirtualSensor(VirtualSensor sensor);

    public VirtualSensor remveVirtualSensor(String sensorId);

    List<VirtualSensor> find();

    VirtualSensor findById(String id);

    VirtualSensor findById(String appkey, String id) throws ApplicationNotExsistException;

    List<VirtualSensor> findByIdArray(String appkey, String ... idArray) throws ApplicationNotExsistException;

    VirtualSensor findByIdAndNotComponentDefinetionEmpty(String id);

    List<VirtualSensor> findByFilter(String appkey, String filter) throws ApplicationNotExsistException;
}
