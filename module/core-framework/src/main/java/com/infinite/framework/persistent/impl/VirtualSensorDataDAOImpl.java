package com.infinite.framework.persistent.impl;

import com.infinite.framework.core.persistent.IMongoDAO;
import com.infinite.framework.persistent.VirtualSensorDataDAO;
import org.springframework.beans.factory.annotation.Autowired;

public class VirtualSensorDataDAOImpl implements VirtualSensorDataDAO {

    @Autowired
    private IMongoDAO mongoDAO;





}
