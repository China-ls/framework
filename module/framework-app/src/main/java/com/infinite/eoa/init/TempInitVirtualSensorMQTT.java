package com.infinite.eoa.init;

import com.infinite.eoa.core.initializing.Initializing;
import com.infinite.eoa.mq.MqttMessageHandler;
import com.infinite.eoa.mq.ReportMessageHandler;
import com.infinite.eoa.service.MqttService;
import com.infinite.eoa.service.SensorEventService;
import com.infinite.eoa.service.VirtualSensorDataService;
import com.infinite.eoa.service.VirtualSensorService;
import com.infinite.eoa.websocket.WebSocketMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class TempInitVirtualSensorMQTT implements Initializing {
    private static Logger log = LoggerFactory.getLogger(TempInitVirtualSensorMQTT.class);

    @Autowired
    private MqttService mqttService;
    @Autowired
    private VirtualSensorDataService virtualSensorDataService;
    @Autowired
    private VirtualSensorService virtualSensorService;
    @Autowired
    private SensorEventService sensorEventService;
    @Autowired
    private WebSocketMessageHandler webSocketMessageHandler;

    @Value("#{dbConfig.debug_without_jms}")
    private boolean debugWithoutJms;

    @Override
    public void initializing() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("start init jms");
        }
        long start = System.currentTimeMillis();

        try {
            if (!debugWithoutJms) {
                mqttService.createConsumer("yinfantech/xgsn/jiaxing/data", new MqttMessageHandler(virtualSensorDataService, webSocketMessageHandler));
                mqttService.createConsumer("yinfantech/xgsn/jiaxing/report", new ReportMessageHandler(sensorEventService, virtualSensorService));
            }
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
