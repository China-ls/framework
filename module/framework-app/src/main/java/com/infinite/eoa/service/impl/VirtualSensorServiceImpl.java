package com.infinite.eoa.service.impl;

import com.infinite.eoa.core.persistent.IMorphiaDAO;
import com.infinite.eoa.core.serivce.AbstractPagerService;
import com.infinite.eoa.core.util.JsonUtil;
import com.infinite.eoa.entity.Department;
import com.infinite.eoa.entity.Employee;
import com.infinite.eoa.entity.EmployeeResourcesLevel;
import com.infinite.eoa.entity.EntityConst;
import com.infinite.eoa.entity.SensorEvent;
import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.persistent.VirtualSensorDAO;
import com.infinite.eoa.service.ApplicationService;
import com.infinite.eoa.service.DepartmentService;
import com.infinite.eoa.service.MqttService;
import com.infinite.eoa.service.VirtualSensorService;
import com.infinite.eoa.service.exception.ApplicationNotExsistException;
import com.mongodb.WriteResult;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

/**
 * @author by hx on 16-7-26.
 */
@Service("VirtualSensorService")
public class VirtualSensorServiceImpl extends AbstractPagerService<VirtualSensor> implements VirtualSensorService {
    private static Logger log = LoggerFactory.getLogger(VirtualSensorServiceImpl.class);

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private VirtualSensorDAO virtualSensorDAO;
    @Autowired
    private MqttService mqttService;
    @Autowired
    private DepartmentService departmentService;

    @Override
    public IMorphiaDAO getMorphiaDAO() {
        return virtualSensorDAO;
    }

    @Override
    public List<VirtualSensor> findByDepartmentIdAndEmployee(Employee employee, String departmentId) {
        boolean shouldLoad = true;
        if (null == employee || employee.getResourcesLevel() == null) {
            return null;
        }
        EmployeeResourcesLevel resLevel = employee.getResourcesLevel();
        if (!resLevel.isAdmin()) {
            ArrayList departments = resLevel.getDepartments();
            shouldLoad = null != departments && departments.contains(departmentId);
        }
        if (shouldLoad) {
            List<Department> departments = departmentService.findEntityIdsByTypeAndDepartmentId(
                    Collections.singletonList(VirtualSensor.TYPE_STATION), departmentId);
            ArrayList<String> entityIds = new ArrayList<String>();
            for (Department dpt : departments) {
                entityIds.add(dpt.getEntity_id());
            }
            return virtualSensorDAO.find(
                    virtualSensorDAO.createQuery().field("sensor_id").in(entityIds)
            ).asList();
        }
        return null;
    }

    @Override
    public List<VirtualSensor> find() {
        return virtualSensorDAO.find().asList();
    }

    @Override
    public VirtualSensor findById(String id) {
        return virtualSensorDAO.findById(id);
    }

    @Override
    public VirtualSensor findById(String appkey, String id) {
        applicationService.applicationExsist(appkey);
        return virtualSensorDAO.findById(id);
    }

    @Override
    public List<VirtualSensor> findByIdArray(String appkey, String... idArray) throws ApplicationNotExsistException {
        applicationService.applicationExsist(appkey);
        if (null != idArray) {
            return virtualSensorDAO.createQuery().field("sensor_id").in(Arrays.asList(idArray)).asList();
        }
        return null;
    }

    @Override
    public VirtualSensor findByIdAndNotComponentDefinetionEmpty(String id) {
        return virtualSensorDAO.find(virtualSensorDAO.createQuery()
                .filter("sensor_id", id)
                .field("components.dataFilterDefinetions").exists()).get();
    }

    @Override
    public VirtualSensor createVirtualSensor(String appid, VirtualSensor sensor) {
        sensor.setSensor_id(UUID.randomUUID().toString());

        String departmentId = sensor.getDepartmentId();
        Department department = null;
        if (!StringUtils.isEmpty(departmentId)) {
            department = departmentService.findById(departmentId);
            sensor.setDepartmentId(departmentId);
            sensor.setDepartment(department);
            sensor.setDepartmentName(department.getName());
        }

        makeEntityDepartment(department, sensor.getName(), sensor.getSensor_id());

        virtualSensorDAO.save(sensor);
        String json = String.format(GSON_ADD, JsonUtil.toJson(sensor));
        mqttService.sendTextMessage("yinfantech/xgsn/jiaxing/system", json);
        return sensor;
    }

    private void makeEntityDepartment(Department department, String name, String entity_id) {
        if (null == department) {
            departmentService.deleteByEntityId(entity_id);
            return;
        }
        Department dptEntity = departmentService.findByEntity_id(entity_id);
        if (null != dptEntity) {
            if (StringUtils.equals(dptEntity.getParentId(), department.getId().toHexString())) {
                if (!StringUtils.equals(dptEntity.getName(), name)) {
                    dptEntity.setName(name);
                    departmentService.save(dptEntity);
                }
                return;
            }
            departmentService.delete(dptEntity);
        }

        dptEntity = new Department();
        dptEntity.setNodeType(Department.NODE_TYPE_ENTITY);
        dptEntity.setName(name);
        dptEntity.setParentId(department.getIdHexString());
        dptEntity.setParentName(department.getName());
        dptEntity.setType(VirtualSensor.TYPE_STATION);
        dptEntity.setLevel(department.getLevel() + 1);
        String name_path = (StringUtils.isBlank(department.getName_path()) ? "," : department.getName_path()) + department.getName() + ",";
        dptEntity.setName_path(name_path);
        String path = (StringUtils.isBlank(department.getPath()) ? "," : department.getPath()) + department.getIdHexString() + ",";
        dptEntity.setPath(path);
        dptEntity.setEntity_id(entity_id);

        departmentService.save(dptEntity);
    }

    @Override
    public VirtualSensor updateVirtualSensor(VirtualSensor sensor) {
        VirtualSensor storedSensor = virtualSensorDAO.findById(sensor.getSensor_id());
        if (null == storedSensor) {
            return null;
        }
        String departmentId = sensor.getDepartmentId();
        Department department = null;
        if (StringUtils.equals(departmentId, storedSensor.getDepartmentId())) {
            department = storedSensor.getDepartment();
        } else if (!StringUtils.isEmpty(departmentId)) {
            department = departmentService.findById(departmentId);
            storedSensor.setDepartmentId(departmentId);
            storedSensor.setDepartment(department);
            storedSensor.setDepartmentName(department.getName());
        }

        storedSensor.setName(sensor.getName());
        storedSensor.setIcon(sensor.getIcon());
        storedSensor.setAddress(sensor.getAddress());
        storedSensor.setAdmin(sensor.getAdmin());
        storedSensor.setComponents(sensor.getComponents());
        storedSensor.setContact(sensor.getContact());
        storedSensor.setControl(sensor.getControl());
        storedSensor.setData(sensor.getAdmin());
        storedSensor.setDay_deal_water_ability(sensor.getDay_deal_water_ability());
        storedSensor.setDesc(sensor.getDesc());
        storedSensor.setIdle_report(sensor.getIdle_report());
        storedSensor.setLatitude(sensor.getLatitude());
        storedSensor.setLongitude(sensor.getLongitude());
        storedSensor.setStatus(sensor.getStatus());
        storedSensor.setVersion(sensor.getVersion());
        storedSensor.setSetup_date(sensor.getSetup_date());
        storedSensor.setStation_type(sensor.getStation_type());

        makeEntityDepartment(department, sensor.getName(), sensor.getSensor_id());

        virtualSensorDAO.save(storedSensor);
        String json = String.format(GSON_UPDATE, sensor.getSensor_id(), JsonUtil.toJson(sensor));
        mqttService.sendTextMessage("yinfantech/xgsn/jiaxing/system", json);
        return sensor;
    }

    @Override
    public int removeVirtualSensor(String sensorId) {
        departmentService.deleteByEntityId(sensorId);
        WriteResult result = virtualSensorDAO.deleteById(sensorId);
        mqttService.sendTextMessage("yinfantech/xgsn/jiaxing/system", String.format(GSON_REMOVE, sensorId));
        return result.getN();
    }

    @Override
    public List<VirtualSensor> findByFilter(String appkey, String filter) throws ApplicationNotExsistException {
        applicationService.applicationExsist(appkey);
        return null;
    }

    @Override
    public int onSensorEventCome(SensorEvent sensorEvent) {
        if (null == sensorEvent) {
            return 0;
        }
        UpdateOperations<VirtualSensor> updateOperations = virtualSensorDAO.createUpdateOperations();
        int event = sensorEvent.getEvent();

        if (event == 0) {
            updateOperations.set("version", sensorEvent.getVersion());
            updateOperations.set("signal", sensorEvent.getSignal());
            updateOperations.set("online", EntityConst.SensorOnline.YES);
        } else if (event == 3) {
            updateOperations.set("signal", sensorEvent.getSignal());
            updateOperations.set("online", EntityConst.SensorOnline.NO);
        } else if (event == 5) {
            updateOperations.set("online", EntityConst.SensorOnline.IDLE);
        }
        UpdateResults updateResults = virtualSensorDAO.update(
                virtualSensorDAO.createQuery().filter("sensor_id", sensorEvent.getSensor_id()),
                updateOperations
        );
        return updateResults.getUpdatedCount();
    }

    @Override
    public List<VirtualSensor> findByStation_typeAndOnlineAndAbility(String station_type, int online, String day_deal_water_ability) {
        Query<VirtualSensor> query = virtualSensorDAO.createQuery();
        if (!StringUtils.isEmpty(station_type)) {
            query.filter("station_type", station_type);
        }
        if (online >= 0) {
            query.filter("online", online);
        }
        if (!StringUtils.isEmpty(day_deal_water_ability)) {
            query.filter("day_deal_water_ability", day_deal_water_ability);
        }
        return virtualSensorDAO.find(query).asList();
    }

    @Override
    public int onSensorDataCome(ArrayList<Document> sensorDatas) {
        if (null == sensorDatas || sensorDatas.size() <= 0) {
            return 0;
        }
        Document document = sensorDatas.get(0);
        String sensor_id = document.getString("sensor_id");
        UpdateOperations<VirtualSensor> updateOperations = virtualSensorDAO.createUpdateOperations();
        updateOperations.set("online", EntityConst.SensorOnline.YES);
        UpdateResults updateResults = virtualSensorDAO.update(
                virtualSensorDAO.createQuery().filter("sensor_id", sensor_id),
                updateOperations
        );
        return updateResults.getUpdatedCount();
    }

    private static final String GSON_ADD = "{\"operation\":\"add\",\"xsensors\":[%s]}";
    private static final String GSON_REMOVE = "{\"operation\":\"remove\",\"xsensors\":[%s]}";
    private static final String GSON_UPDATE = "{\"operation\":\"update\",\"sensor_id\":\"%s\",\"xsensors\":[%s]}";
}
