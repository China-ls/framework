package com.infinite.eoa.service;

import com.infinite.eoa.core.serivce.IPagerService;
import com.infinite.eoa.entity.Employee;
import com.infinite.eoa.entity.SensorEvent;
import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.service.exception.ApplicationNotExsistException;
import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface VirtualSensorService extends IPagerService<VirtualSensor> {

    public VirtualSensor createVirtualSensor(String appkey, VirtualSensor sensor);

    public VirtualSensor updateVirtualSensor(VirtualSensor sensor);

    public int removeVirtualSensor(String sensorId);

    List<VirtualSensor> findByDepartmentIdAndEmployee(Employee employee, String departmentId);

    List<VirtualSensor> find();

    VirtualSensor findById(String id);

    VirtualSensor findById(String appkey, String id) throws ApplicationNotExsistException;

    List<VirtualSensor> findByIdArray(String appkey, String... idArray) throws ApplicationNotExsistException;

    VirtualSensor findByIdAndNotComponentDefinetionEmpty(String id);

    List<VirtualSensor> findByFilter(String appkey, String filter) throws ApplicationNotExsistException;

    int onSensorEventCome(SensorEvent sensorEvent);

    List<VirtualSensor> findByStation_typeAndOnlineAndAbility(String station_type, int online, String day_deal_water_ability);

    int onSensorDataCome(ArrayList<Document> sensorDatas);
}
