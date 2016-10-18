package com.infinite.eoa.mq;

import com.infinite.eoa.core.jms.spring.mqtt.JmsMessageHandlerAdapter;
import com.infinite.eoa.core.util.JsonUtil;
import com.infinite.eoa.entity.VirtualSensorData;
import com.infinite.eoa.service.VirtualSensorDataService;
import com.infinite.eoa.service.VirtualSensorService;
import com.infinite.eoa.websocket.WebSocketMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

import java.util.List;

/**
 * @author by hx on 16-8-22.
 */
public class MqttMessageHandler extends JmsMessageHandlerAdapter {
    private static Logger log = LoggerFactory.getLogger(MqttMessageHandler.class);

    private VirtualSensorDataService virtualSensorDataService;
    private VirtualSensorService virtualSensorService;
    private WebSocketMessageHandler webSocketMessageHandler;
    private String callbackUrl = "http://127.0.0.1:9998/app/callback/jms";

    public MqttMessageHandler(VirtualSensorDataService virtualSensorDataService, WebSocketMessageHandler webSocketMessageHandler) {
        this.virtualSensorDataService = virtualSensorDataService;
        this.webSocketMessageHandler = webSocketMessageHandler;
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        Object payload = message.getPayload();
        if (log.isDebugEnabled()) {
            log.debug("handler payload: {}", payload);
        }
        if (payload instanceof String) {
            String text = (String) payload;
            try {
                List<VirtualSensorData> documentList = virtualSensorDataService.save(text);
                //TODO 根据 VirtualSensor 里面配置的callback来回调
                text = JsonUtil.toJson(documentList);
                if (log.isDebugEnabled()) {
                    log.debug("data [data:{}].", text);
                }
                webSocketMessageHandler.broadcaseMessage(text);
            } catch (Exception e) {
                if (log.isErrorEnabled()) {
                    log.error("error [data:{}]", text, e);
                }
            }
        }
    }
}
