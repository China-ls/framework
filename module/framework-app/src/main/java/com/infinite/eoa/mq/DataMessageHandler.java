package com.infinite.eoa.mq;

import com.infinite.eoa.core.jms.spring.mqtt.JmsMessageHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessagingException;

/**
 * @author by hx on 16-8-22.
 */
public class DataMessageHandler extends JmsMessageHandlerAdapter {
    private static Logger LOG = LoggerFactory.getLogger(DataMessageHandler.class);

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        Object payload = message.getPayload();
        if (payload instanceof String) {
            String text = (String) payload;
            if (LOG.isDebugEnabled()) {
                LOG.debug("data -- [data:{}].", text);
            }
        }
    }
}
