package com.infinite.framework.service;

import org.springframework.messaging.MessageHandler;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface MqttService {

    public void createConsumer(String destination, MessageHandler handler);

    public void removeConsumer(String destination);

    public void sendTextMessage(String destination, String message);

}
