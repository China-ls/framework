package com.infinite.water.service.impl;

import com.infinite.water.core.util.HttpUtils;
import com.infinite.water.core.util.JsonUtil;
import com.infinite.water.entity.VirtualSensor;
import com.infinite.water.service.VirtualSensorService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

@Service("VirtualSensorService")
public class VirtualSensorServiceImpl implements VirtualSensorService {
    @Override
    public VirtualSensor getVirtualSensor(String id) {
        String json = HttpUtils.get("http://localhost:9998/framework/sensor/" + id);
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return JsonUtil.fromJson(json, VirtualSensor.class);
    }
}
