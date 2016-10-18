package com.infinite.eoa.service.impl;

import com.infinite.eoa.core.persistent.IMorphiaDAO;
import com.infinite.eoa.core.serivce.AbstractPagerService;
import com.infinite.eoa.entity.EntityConst;
import com.infinite.eoa.entity.SensorEvent;
import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.persistent.VirtualSensorDAO;
import com.infinite.eoa.service.ApplicationService;
import com.infinite.eoa.service.VirtualSensorService;
import com.infinite.eoa.service.exception.ApplicationNotExsistException;
import org.apache.commons.lang.StringUtils;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @author by hx on 16-7-26.
 */
@Service("VirtualSensorService")
public class VirtualSensorServiceImpl extends AbstractPagerService<VirtualSensor> implements VirtualSensorService {
//    private static Logger log = LoggerFactory.getLogger(VirtualSensorServiceImpl.class);

    private ApplicationService applicationService;
    private VirtualSensorDAO virtualSensorDAO;

    @Autowired
    public void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Autowired
    public void setVirtualSensorDAO(VirtualSensorDAO virtualSensorDAO) {
        this.virtualSensorDAO = virtualSensorDAO;
    }

    @Override
    public IMorphiaDAO getMorphiaDAO() {
        return virtualSensorDAO;
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
}
