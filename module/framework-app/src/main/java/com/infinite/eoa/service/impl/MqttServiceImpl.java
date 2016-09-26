package com.infinite.eoa.service.impl;

import com.infinite.eoa.core.jms.spring.mqtt.JmsRouterListener;
import com.infinite.eoa.service.MqttService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.support.MutableMessage;
import org.springframework.messaging.MessageHandler;
import org.springframework.stereotype.Service;

/**
 * @author by hx on 16-7-26.
 */
@Service("MqttService")
public class MqttServiceImpl implements MqttService, InitializingBean {

    @Qualifier("jmsAdapter")
    @Autowired
    private MqttPahoMessageDrivenChannelAdapter channelAdapter;
    @Qualifier("jmsPublisher")
    @Autowired
    private MqttPahoMessageHandler mqttPahoMessageHandler;

    @Qualifier("jmsChannel")
    @Autowired
    private PublishSubscribeChannel messageChannel;

    @Autowired
    private JmsRouterListener jmsRouterListener;

    @Override
    public void createConsumer(String destination, MessageHandler handler) {
        channelAdapter.addTopic(destination);
        JmsRouterListener.registHandler(destination, handler);
    }

    @Override
    public void removeConsumer(String destination) {
        channelAdapter.removeTopic(destination);
        JmsRouterListener.unregistHandler(destination);
    }

    @Override
    public void sendTextMessage(String destination, final String message) {
        MutableMessage<String> textMessage = new MutableMessage<String>(message);
        textMessage.getHeaders().put("mqtt_topic", destination);
        mqttPahoMessageHandler.handleMessage(textMessage);
//        boolean send = messageChannel.send(textMessage);
//        System.out.println( "send msg to mq: " + send );
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        messageChannel.subscribe(jmsRouterListener);
    }
}
