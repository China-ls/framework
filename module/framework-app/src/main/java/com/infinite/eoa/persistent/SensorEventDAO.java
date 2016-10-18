package com.infinite.eoa.persistent;

import com.infinite.eoa.core.persistent.IMorphiaDAO;
import com.infinite.eoa.entity.SensorEvent;

public interface SensorEventDAO extends IMorphiaDAO<SensorEvent, String> {

    SensorEvent findFirstByTimeDescAndSensorId(String sensor_id);
}
