package com.infinite.framework.core.jms.spring.mqtt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jms.*;
import java.util.HashMap;

/**
 * @author by hx on 16-7-26.
 */
public class JmsRouterListener implements MessageListener {
    private static Logger log = LoggerFactory.getLogger(JmsRouterListener.class);

    private HashMap<String, JmsMessageHandler> handlerMap = new HashMap<String, JmsMessageHandler>(0);

    public void setHandlerMap(HashMap<String, JmsMessageHandler> handlerMap) {
        this.handlerMap = handlerMap;
    }

    public HashMap<String, JmsMessageHandler> getHandlerMap() {
        return handlerMap;
    }

    public void registHandler(String destination, JmsMessageHandler handler) {
        this.handlerMap.put(destination, handler);
    }

    public void unregistHandler(String destination) {
        this.handlerMap.remove(destination);
    }

    @Override
    public void onMessage(Message message) {
        try {
            Destination destination = message.getJMSDestination();
            JmsMessageHandler handler = handlerMap.get(destination.toString());
            if (null == handler) {
                if (log.isDebugEnabled()) {
                    log.debug("no mapped handler for destination:{}", destination);
                }
                return;
            }
            if (message instanceof TextMessage) {
                if (log.isDebugEnabled()) {
                    log.debug("recv text message : {}", ((TextMessage) message).getText());
                }
                handler.handlerTextMessage((TextMessage) message);
            } else if (message instanceof ObjectMessage) {
                if (log.isDebugEnabled()) {
                    log.debug("recv obj message : {}", ((ObjectMessage) message).getObject());
                }
                handler.handlerObjectMessage((ObjectMessage) message);
            } else if (message instanceof BytesMessage) {
                if (log.isDebugEnabled()) {
                    log.debug("recv obj message : {}", message);
                }
                handler.handlerBytesMessage((BytesMessage) message);
            } else if (message instanceof MapMessage) {
                if (log.isDebugEnabled()) {
                    log.debug("recv obj message : {}", message);
                }
                handler.handlerMapMessage((MapMessage) message);
            } else if (message instanceof StreamMessage) {
                if (log.isDebugEnabled()) {
                    log.debug("recv obj message : {}", ((StreamMessage) message).toString());
                }
                handler.handlerStreamMessage((StreamMessage) message);
            }
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
}
