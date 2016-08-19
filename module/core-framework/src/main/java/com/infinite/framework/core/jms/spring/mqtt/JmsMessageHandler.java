package com.infinite.framework.core.jms.spring.mqtt;

import org.springframework.messaging.MessageHandler;

/**
 * @author by hx on 16-7-26.
 */
public interface JmsMessageHandler extends MessageHandler {

    boolean supportDestination(String destination);

}
