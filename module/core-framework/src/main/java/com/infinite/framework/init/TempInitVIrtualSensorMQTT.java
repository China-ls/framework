package com.infinite.framework.init;

import com.infinite.framework.mqtt.FilterJmsMessageHandler;
import com.infinite.framework.service.MqttService;
import org.apache.activemq.command.ActiveMQTopic;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

public class TempInitVIrtualSensorMQTT implements InitializingBean {
    private static Logger log = LoggerFactory.getLogger(TempInitVIrtualSensorMQTT.class);

    @Autowired
    private MqttService mqttService;
    @Autowired
    private FilterJmsMessageHandler jmsMessageHandler;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("start init database");
        }
        long start = System.currentTimeMillis();

        try {
            mqttService.createConsumer(new ActiveMQTopic("yinfantech/xgsn/jiaxing/data"), jmsMessageHandler);
        } catch (Throwable e) {
            if (log.isErrorEnabled()) {
                log.error("error get xsensor.json data", e);
            }
        } finally {
        }

        long end = System.currentTimeMillis();
        long cost = end - start;
        if (log.isDebugEnabled()) {
            log.debug("finish init database, cose : {} millseconds", cost);
        }
    }
}
