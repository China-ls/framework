package com.infinite.framework.router.controller;

import com.infinite.framework.core.jms.spring.mqtt.JmsMessageHandlerAdapter;
import com.infinite.framework.core.web.BasicRestController;
import com.infinite.framework.entity.VirtualSensor;
import com.infinite.framework.service.VirtualSensorService;
import com.infinite.framework.service.impl.MqttServiceImpl;
import org.apache.activemq.command.ActiveMQTopic;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.TextMessage;

/**
 * @author by hx on 16-7-4.
 */
@RequestMapping("/test")
@RestController("TestController")
public class TestController extends BasicRestController {
    private static Logger log = LoggerFactory.getLogger(TestController.class);

    @Autowired
    private MqttServiceImpl mqttService;
    @Autowired
    @Qualifier("topicDestination")
    private Destination destination;

    @Autowired
    private VirtualSensorService sensorService;

    @RequestMapping("/test")
    @ResponseBody
    public String test() {
        sensorService.createVirtualSensor(null, null);
        return "test";
    }

    @RequestMapping("/jms/listen/{destination}")
    @ResponseBody
    public String listenTopic(@PathVariable("destination") String destination) {
        log.debug("listen topci : {}", destination);
        mqttService.createConsumer(new ActiveMQTopic(destination), new JmsMessageHandlerAdapter() {
            @Override
            public void handlerTextMessage(TextMessage message) throws JMSException {
                log.debug("recv text: {}", message.getText());
            }
        });
        return "listen";
    }

    @RequestMapping("/jms/unlisten/{destination}")
    @ResponseBody
    public String unlistenTopic(@PathVariable("destination") String destination) {
        log.debug("unlisten topci : {}", destination);
        mqttService.removeConsumer(new ActiveMQTopic(destination));
        return "unlisten";
    }

    @RequestMapping("/jms/topic")
    @ResponseBody
    public String sendToTopic() {
        for (int i = 0; i < 2; i++) {
            mqttService.sendTextMessage(destination, "你好，生产者！这是消息：" + (i + 1));
        }
        return "success";
    }

    @RequestMapping("/jms/send")
    @ResponseBody
    public String sendToTopicDM(
            @ModelAttribute("destination") String destination,
            @ModelAttribute("message") String message) {
        mqttService.sendTextMessage(new ActiveMQTopic(destination), message);
        return "success";
    }

    @RequestMapping("/roles")
    @ResponseBody
    public String roles() {
        log.debug("login");
        UsernamePasswordToken token = new UsernamePasswordToken();
        token.setPassword("test".toCharArray());
        token.setUsername("test");
        login(token);
        return "response by : testShiroReuireRole";
    }

    @RequestMapping("/vs/dd/{id}")
    @ResponseBody
    public VirtualSensor getVsByDataDefinition(@PathVariable("id") String id) {
        return sensorService.findByIdAndNotComponentDefinetionEmpty(id);
    }

}
