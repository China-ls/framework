package com.infinite.framework.mqtt;

import com.infinite.framework.core.jms.spring.mqtt.JmsMessageHandlerAdapter;
import com.infinite.framework.service.VirtualSensorDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.TaskExecutor;

import javax.jms.JMSException;
import javax.jms.TextMessage;

public class FilterJmsMessageHandler extends JmsMessageHandlerAdapter {

    @Qualifier("threadPoolTaskExecutor")
    @Autowired
    private TaskExecutor taskExecutor;

    @Autowired
    private VirtualSensorDataService virtualSensorDataService;

    @Override
    public void handlerTextMessage(TextMessage message) throws JMSException {
        taskExecutor.execute(new TempJmsFilter(message.getText()));
        virtualSensorDataService.save(message.getText());
    }

}
