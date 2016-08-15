package com.infinite.framework.service;

import javax.jms.Destination;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface MqService {

    void sendMessage(Destination destination, String message);

}
