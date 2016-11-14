package com.infinite.eoa.mq;

import com.infinite.eoa.core.jms.spring.mqtt.JmsMessageHandlerAdapter;
import com.infinite.eoa.core.util.JsonUtil;
import com.infinite.eoa.entity.SensorEvent;
import com.infinite.eoa.service.SensorEventService;
import com.infinite.eoa.service.VirtualSensorService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

/**
 * @author by hx on 16-8-22.
 */
public class ReportMessageHandler extends JmsMessageHandlerAdapter {
    private static Logger LOG = LoggerFactory.getLogger(ReportMessageHandler.class);

    private SensorEventService sensorEventService;
    private VirtualSensorService virtualSensorService;

    public ReportMessageHandler(SensorEventService sensorEventService, VirtualSensorService virtualSensorService) {
        this.sensorEventService = sensorEventService;
        this.virtualSensorService = virtualSensorService;
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        Object payload = message.getPayload();
        if (payload instanceof String) {
            String text = (String) payload;
            if (LOG.isDebugEnabled()) {
                LOG.debug(text);
            }
            try {
                SensorEvent sensorEvent = JsonUtil.fromJson(text, SensorEvent.class);
                sensorEvent.setTime(sensorEvent.getTime() * 1000);
                sensorEvent = sensorEventService.save(sensorEvent);
                int updateCount = virtualSensorService.onSensorEventCome(sensorEvent);
                if (LOG.isTraceEnabled()) {
                    LOG.trace("report -- [id:{}, updateCount:{}]", sensorEvent.getSensor_id(), updateCount);
                }
            } catch (Exception e) {
                if (LOG.isErrorEnabled()) {
                    LOG.error(e.getMessage());
                }
            }
        }
    }
}
