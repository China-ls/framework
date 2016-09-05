package com.infinite.framework.init;

import com.infinite.framework.core.initializing.Initializing;
import com.infinite.framework.mq.MqttMessageHandler;
import com.infinite.framework.service.MqttService;
import com.infinite.framework.service.VirtualSensorDataService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class TempInitVirtualSensorMQTT implements Initializing {
    private static Logger log = LoggerFactory.getLogger(TempInitVirtualSensorMQTT.class);

    @Autowired
    private MqttService mqttService;
    @Autowired
    private VirtualSensorDataService virtualSensorDataService;

    @Override
    public void initializing() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("start init jms");
        }
        long start = System.currentTimeMillis();

        try {
            mqttService.createConsumer("yinfantech/xgsn/jiaxing/data", new MqttMessageHandler(virtualSensorDataService));
        } catch (Throwable e) {
            if (log.isErrorEnabled()) {
                log.error("error init mqtt", e);
            }
        }

        long end = System.currentTimeMillis();
        long cost = end - start;
        if (log.isDebugEnabled()) {
            log.debug("finish init jms, cose : {} millseconds", cost);
        }
    }
}
