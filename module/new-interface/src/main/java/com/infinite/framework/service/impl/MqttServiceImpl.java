package com.infinite.framework.service.impl;

import com.infinite.framework.core.jms.spring.mqtt.JmsMessageHandler;
import com.infinite.framework.core.jms.spring.mqtt.JmsRouterListener;
import com.infinite.framework.service.MqttService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.jms.listener.DefaultMessageListenerContainer;
import org.springframework.stereotype.Service;

import javax.jms.*;
import java.io.Serializable;

/**
 * @author by hx on 16-7-26.
 */
@Service("MqttService")
public class MqttServiceImpl implements MqttService {
    @Autowired
    private JmsTemplate jmsTemplate;
    @Autowired
    private ConnectionFactory connectionFactory;
    @Autowired
    private BeanServiceImpl beanService;
    @Autowired
    private JmsRouterListener listener;

    @Override
    public void createConsumer(Destination destination, JmsMessageHandler handler) {
        String beanName = getJmsListenerBeanName(destination);
        BeanDefinitionBuilder definition = BeanDefinitionBuilder.genericBeanDefinition(DefaultMessageListenerContainer.class);
        definition.addPropertyReference("connectionFactory", "targetConnectionFactory");
        definition.addPropertyValue("destination", destination);
        definition.addPropertyValue("messageListener", listener);
        beanService.registerBean(beanName, definition.getBeanDefinition());
        DefaultMessageListenerContainer container = (DefaultMessageListenerContainer) beanService.getBean(beanName);
        container.start();
        listener.registHandler(destination.toString(), handler);
    }

    @Override
    public void removeConsumer(Destination destination) {
        String beanName = getJmsListenerBeanName(destination);
        DefaultMessageListenerContainer container = (DefaultMessageListenerContainer) beanService.getBean(beanName);
        container.destroy();
        beanService.removeBean(destination.toString() + "#ListenerBean");
        listener.unregistHandler(destination.toString());
    }

    @Override
    public void sendTextMessage(Destination destination, final String message) {
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
    }

    @Override
    public void sendObjectMessage(Destination destination, final Serializable message) {
        jmsTemplate.send(destination, new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(message);
            }
        });
    }

    @Override
    public void convertAndSend(Destination destination, Object message) {
        jmsTemplate.convertAndSend(destination, message);
    }

    String getJmsListenerBeanName(Destination destination) {
        return destination.toString() + "#ListenerBean";
    }
}
