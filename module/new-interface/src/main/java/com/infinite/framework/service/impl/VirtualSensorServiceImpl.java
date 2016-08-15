package com.infinite.framework.service.impl;

import com.infinite.framework.entity.VirtualSensor;
import com.infinite.framework.persistent.VirtualSensorDAO;
import com.infinite.framework.service.VirtualSensorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author by hx on 16-7-26.
 */
@Service("VirtualSensorService")
public class VirtualSensorServiceImpl implements VirtualSensorService {
    private static Logger log = LoggerFactory.getLogger(VirtualSensorServiceImpl.class);

    @Autowired
    private VirtualSensorDAO virtualSensorDAO;

    @Override
    public VirtualSensor findById(String id) {
        return virtualSensorDAO.findById(id);
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
//            Document findOneSensor = mongoDAO.findFirst(collection, Filters_bak.eq("id", sensor.getId()));
//
//            if (null != findOneSensor) {
//                collection.findOneAndUpdate(Filters_bak.eq("id", sensor.getId()), sensor.toDocument());
//                collection.findOneAndReplace(Filters_bak.eq("id", sensor.getId()), sensor.toDocument());
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
}
