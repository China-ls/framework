package com.infinite.framework.core.jms.spring.mqtt;

import javax.jms.*;

/**
 * @author by hx on 16-7-26.
 */
public interface JmsMessageHandler {

    void handlerTextMessage(TextMessage message) throws JMSException;

    void handlerObjectMessage(ObjectMessage message) throws JMSException;

    void handlerBytesMessage(BytesMessage message) throws JMSException;

    void handlerStreamMessage(StreamMessage message) throws JMSException;

    void handlerMapMessage(MapMessage message) throws JMSException;
}
