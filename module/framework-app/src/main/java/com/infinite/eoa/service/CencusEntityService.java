package com.infinite.eoa.service;

import com.infinite.eoa.entity.cencus.ComponentDayWorkCencus;
import com.infinite.eoa.entity.cencus.SensorDayElectricUseageCencus;
import com.infinite.eoa.entity.cencus.SensorDayHandlerWaterCencus;
import com.infinite.eoa.entity.cencus.SensorDayHighWaterLevelCountCencus;
import com.infinite.eoa.entity.cencus.SensorDayMolErrorCountCencus;

import java.util.Date;
import java.util.List;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface CencusEntityService {

    public List<SensorDayMolErrorCountCencus> findSensorDayMolErrorCountCencusByTimeRange(
            String sensor_id, Date startDate, Date endDate);

    public List<SensorDayHighWaterLevelCountCencus> findSensorDayHighWaterLevelCountCencusByTimeRange(
            String sensor_id, String comp_id, Date startDate, Date endDate);

    public List<SensorDayElectricUseageCencus> findSensorDayElectricUseageCencusByTimeRange(
            String sensor_id, Date startDate, Date endDate);

    public List<SensorDayHandlerWaterCencus> findSensorDayHandlerWaterCencusByTimeRange(
            String sensor_id, Date startDate, Date endDate);

    public List<ComponentDayWorkCencus> findComponentDayWorkCencusByTimeRange(
            String sensor_id, String comp_id, Date startDate, Date endDate);
}
