package com.infinite.framework.mq;

import com.infinite.framework.core.jms.spring.mqtt.JmsMessageHandlerAdapter;
import com.infinite.framework.core.util.HttpUtils;
import com.infinite.framework.core.util.JsonUtil;
import com.infinite.framework.service.VirtualSensorDataService;
import com.infinite.framework.service.VirtualSensorService;
import org.apache.http.message.BasicNameValuePair;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

import java.io.IOException;
import java.util.List;

/**
 * @author by hx on 16-8-22.
 */
public class MqttMessageHandler extends JmsMessageHandlerAdapter {
    private static Logger log = LoggerFactory.getLogger(MqttMessageHandler.class);

    private VirtualSensorDataService virtualSensorDataService;
    private VirtualSensorService virtualSensorService;

    private String callbackUrl = "http://127.0.0.1:9998/app/callback/jms";

    public MqttMessageHandler(VirtualSensorDataService virtualSensorDataService) {
        this.virtualSensorDataService = virtualSensorDataService;
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        Object payload = message.getPayload();
//        if (log.isDebugEnabled()) {
//            log.debug("handler payload: {}", payload);
//        }
        if (payload instanceof String) {
            String text = (String) payload;
            List<Document> documentList = virtualSensorDataService.save(text);
            //TODO 根据 VirtualSensor 里面配置的callback来回调
//            VirtualSensor sensor = (null == documentList || documentList.size() <= 0)
//                    ? null : virtualSensorService.findById(documentList.get(0).getString("sensor_id"));
//            if (null != sensor) {
            text = JsonUtil.toJson(documentList);
            try {
                HttpUtils.post(callbackUrl,
                        new BasicNameValuePair("message", text)
                );
                if (log.isDebugEnabled()) {
                    log.debug("success call back [url:{}, data:{}].", callbackUrl, text);
                }
            } catch (IOException e) {
                if (log.isErrorEnabled()) {
                    log.error("error call back [message:{}, data:{}]", e.getMessage(), text);
                }
            }
//            }
        }
    }
}
