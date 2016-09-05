package com.infinite.framework.core.jms.spring.mqtt;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;

import java.util.HashMap;

/**
 * @author by hx on 16-7-26.
 */
public class JmsRouterListener extends JmsMessageHandlerAdapter {
    private static Logger log = LoggerFactory.getLogger(JmsRouterListener.class);

    private static HashMap<String, MessageHandler> handlerMap = new HashMap<String, MessageHandler>(0);

    private boolean enableDebug = true;

    public static HashMap<String, MessageHandler> getHandlerMap() {
        return handlerMap;
    }

    public static void registHandler(String destination, MessageHandler handler) {
        if (StringUtils.isEmpty(destination) || null == handler) {
            return;
        }
        synchronized (handlerMap) {
            handlerMap.put(destination, handler);
        }
    }

    public static void unregistHandler(String destination) {
        if (StringUtils.isEmpty(destination)) {
            return;
        }
        synchronized (handlerMap) {
            handlerMap.remove(destination);
        }
    }

    public void setEnableDebug(boolean enableDebug) {
        this.enableDebug = enableDebug;
    }

    public boolean isEnableDebug() {
        return enableDebug;
    }

    @Override
    public void handleMessage(Message<?> message) throws MessagingException {
        try {
            if (isEnableDebug() && log.isDebugEnabled()) {
                log.debug("recieve message:{}", message);
            }
            String destination = (String) message.getHeaders().get("mqtt_topic");
            MessageHandler handler = handlerMap.get(destination);
            if (null == handler) {
                if (log.isDebugEnabled()) {
                    log.debug("no mapped handler for destination:{}", destination);
                }
            } else {
                handler.handleMessage(message);
            }
        } catch (Throwable e) {
            if (isEnableDebug() && log.isErrorEnabled()){
                log.error("error handler message: {}", message, e);
            }
        }
    }
}
