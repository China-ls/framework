package com.infinite.framework.init;

import com.infinite.framework.service.MqttService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

public class TempInitVirtualSensorMQTT implements InitializingBean {
    private static Logger log = LoggerFactory.getLogger(TempInitVirtualSensorMQTT.class);

    @Autowired
    private MqttService mqttService;

    @Override
    public void afterPropertiesSet() throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("start init database");
        }
        long start = System.currentTimeMillis();

        try {
            mqttService.createConsumer("test", new MessageHandler() {
                @Override
                public void handleMessage(Message<?> message) throws MessagingException {
                    System.out.println("---from test : " + message);
                }
            });
        } catch (Throwable e) {
            if (log.isErrorEnabled()) {
                log.error("error init mqtt", e);
            }
        }

        long end = System.currentTimeMillis();
        long cost = end - start;
        if (log.isDebugEnabled()) {
            log.debug("finish init database, cose : {} millseconds", cost);
        }
    }
}
