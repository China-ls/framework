package com.infinite.framework.service;

import com.infinite.framework.core.jms.spring.mqtt.JmsMessageHandler;

import javax.jms.Destination;
import java.io.Serializable;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface MqttService {

    public void createConsumer(Destination destination, JmsMessageHandler handler);

    public void removeConsumer(Destination destination);

    public void sendTextMessage(Destination destination, String message);

    public void sendObjectMessage(Destination destination, final Serializable message);

    public void convertAndSend(Destination destination, final Object message);

}
