package com.infinite.framework.service.impl;

import com.infinite.framework.service.MqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

/**
 * @author by hx on 16-7-26.
 */
@Service("TestMqService")
public class TestMqService implements MqService {
    @Autowired
    private JmsTemplate jmsTemplate;

    @Override
    public void sendMessage(Destination destination, final String message) {
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });

    }
}
