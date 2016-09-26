package com.infinite.eoa.persistent;

import com.infinite.eoa.core.persistent.IMorphiaDAO;
import com.infinite.eoa.entity.VirtualSensor;

public interface VirtualSensorDAO extends IMorphiaDAO<VirtualSensor, String> {

    VirtualSensor findById(String id);

}
