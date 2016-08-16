package com.infinite.water.service.impl;

import com.google.gson.reflect.TypeToken;
import com.infinite.water.core.util.HttpUtils;
import com.infinite.water.core.util.JsonUtil;
import com.infinite.water.entity.ServerConfig;
import com.infinite.water.entity.VirtualSensor;
import com.infinite.water.service.VirtualSensorService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("VirtualSensorService")
public class VirtualSensorServiceImpl implements VirtualSensorService {

    @Autowired
    private ServerConfig serverConfig;

    @Override
    public List<VirtualSensor> getAllVirtualSensor() {
        String json = HttpUtils.get(serverConfig.getServerUrl() + "/sensor/all");
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return JsonUtil.fromJson(json, new TypeToken<List<VirtualSensor>>() {}.getType());
    }

    @Override
    public VirtualSensor getVirtualSensor(String id) {
        String json = HttpUtils.get(serverConfig.getServerUrl() + "/sensor/" + id);
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return JsonUtil.fromJson(json, VirtualSensor.class);
    }
}
