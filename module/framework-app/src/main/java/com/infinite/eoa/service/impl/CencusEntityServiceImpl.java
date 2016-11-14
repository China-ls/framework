package com.infinite.eoa.service.impl;

import com.infinite.eoa.core.persistent.IMorphiaDAO;
import com.infinite.eoa.entity.cencus.ComponentDayWorkCencus;
import com.infinite.eoa.entity.cencus.SensorDayElectricUseageCencus;
import com.infinite.eoa.entity.cencus.SensorDayHandlerWaterCencus;
import com.infinite.eoa.entity.cencus.SensorDayHighWaterLevelCountCencus;
import com.infinite.eoa.entity.cencus.SensorDayMolErrorCountCencus;
import com.infinite.eoa.persistent.ComponentDayWorkCencusDAO;
import com.infinite.eoa.persistent.SensorDayElectricUseageCencusDAO;
import com.infinite.eoa.persistent.SensorDayHandlerWaterCencusDAO;
import com.infinite.eoa.persistent.SensorDayHighWaterLevelCountCencusDAO;
import com.infinite.eoa.persistent.SensorDayMolErrorCountCencusDAO;
import com.infinite.eoa.service.CencusEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class CencusEntityServiceImpl implements CencusEntityService {

    @Autowired
    private SensorDayMolErrorCountCencusDAO dayMolErrorCountCencusDAO;
    @Autowired
    private SensorDayHighWaterLevelCountCencusDAO dayHighWaterLevelCountCencusDAO;
    @Autowired
    private SensorDayElectricUseageCencusDAO dayElectricUseageCencusDAO;
    @Autowired
    private SensorDayHandlerWaterCencusDAO dayHandlerWaterCencusDAO;
    @Autowired
    private ComponentDayWorkCencusDAO componentDayWorkCencusDAO;

    @Override
    public List<SensorDayMolErrorCountCencus> findSensorDayMolErrorCountCencusByTimeRange(String sensor_id, Date startDate, Date endDate) {
        return findSensorTimeRangeData(dayMolErrorCountCencusDAO, sensor_id, startDate, endDate);
    }

    @Override
    public List<SensorDayHighWaterLevelCountCencus> findSensorDayHighWaterLevelCountCencusByTimeRange(String sensor_id, String comp_id, Date startDate, Date endDate) {
        return dayHighWaterLevelCountCencusDAO.find(
                dayHighWaterLevelCountCencusDAO.createQuery()
                        .filter("sensor_id", sensor_id)
                        .filter("comp_id", comp_id)
                        .field("time").greaterThan(startDate)
                        .field("time").lessThan(endDate)
                        .order("time")
        ).asList();
    }

    @Override
    public List<SensorDayElectricUseageCencus> findSensorDayElectricUseageCencusByTimeRange(String sensor_id, Date startDate, Date endDate) {
        return findSensorTimeRangeData(dayElectricUseageCencusDAO, sensor_id, startDate, endDate);
    }

    @Override
    public List<SensorDayHandlerWaterCencus> findSensorDayHandlerWaterCencusByTimeRange(String sensor_id, Date startDate, Date endDate) {
        return findSensorTimeRangeData(dayHandlerWaterCencusDAO, sensor_id, startDate, endDate);
    }

    @Override
    public List<ComponentDayWorkCencus> findComponentDayWorkCencusByTimeRange(String sensor_id, String comp_id, Date startDate, Date endDate) {
        return componentDayWorkCencusDAO.find(
                componentDayWorkCencusDAO.createQuery()
                        .filter("sensor_id", sensor_id)
                        .filter("comp_id", comp_id)
                        .field("time").greaterThan(startDate)
                        .field("time").lessThan(endDate)
                        .order("time")
        ).asList();
    }

    private <T, ID> List<T> findSensorTimeRangeData(IMorphiaDAO<T, ID> dao, String sensor_id, Date startDate, Date endDate) {
        return dao.find(
                dao.createQuery()
                        .filter("sensor_id", sensor_id)
                        .field("time").greaterThan(startDate)
                        .field("time").lessThan(endDate)
                        .order("time")
        ).asList();
    }
}
