package com.infinite.framework.persistent;

import com.infinite.framework.core.persistent.IMorphiaDAO;
import com.infinite.framework.entity.VirtualSensor;

public interface VirtualSensorDAO extends IMorphiaDAO<VirtualSensor, String> {

    VirtualSensor findById(String id);

}
