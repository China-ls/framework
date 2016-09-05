package com.infinite.framework.service.impl;

import com.infinite.framework.entity.VirtualSensor;
import com.infinite.framework.persistent.VirtualSensorDAO;
import com.infinite.framework.service.ApplicationService;
import com.infinite.framework.service.VirtualSensorService;
import com.infinite.framework.service.exception.ApplicationNotExsistException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author by hx on 16-7-26.
 */
@Service("VirtualSensorService")
public class VirtualSensorServiceImpl implements VirtualSensorService {
    private static Logger log = LoggerFactory.getLogger(VirtualSensorServiceImpl.class);

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private VirtualSensorDAO virtualSensorDAO;

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
    public VirtualSensor createVirtualSensor(/*String clientid,*/ String appid, VirtualSensor sensor) {
//        MongoCollection<Document> collection = mongoDAO.getCollection(dbName, collectionName);
//        Document documentApp = mongoDAO.findFirst(collection, Filters_bak.eq("", appid));
//        if (null == documentApp) {
//            if (log.isDebugEnabled()) {
//                log.debug("should not create Virtual Sensor because appkey [{}] not exsist!", appid);
//            }
//            return null;
//        } else {
//            Application application = new Application();
//            application.fromDocument(documentApp);
//
//            application.addSensors();
//
//            Document findOneSensor = mongoDAO.findFirst(collection, Filters_bak.eq("id", sensor.getSensor_id()));
//
//            if (null != findOneSensor) {
//                collection.findOneAndUpdate(Filters_bak.eq("id", sensor.getSensor_id()), sensor.toDocument());
//                collection.findOneAndReplace(Filters_bak.eq("id", sensor.getSensor_id()), sensor.toDocument());
//            } else {
//                collection.insertOne(sensor.toDocument());
//            }
//            return sensor;
//        }
        return null;
    }

    @Override
    public VirtualSensor updateVirtualSensor(VirtualSensor sensor) {
        return null;
    }

    @Override
    public VirtualSensor remveVirtualSensor(String sensorId) {
        return null;
    }

    @Override
    public List<VirtualSensor> findByFilter(String appkey, String filter) throws ApplicationNotExsistException {
        applicationService.applicationExsist(appkey);
        return null;
    }
}
