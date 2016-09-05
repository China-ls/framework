package com.infinite.framework.router.controller;

import com.infinite.framework.core.web.BasicRestController;
import com.infinite.framework.entity.VirtualSensor;
import com.infinite.framework.service.VirtualSensorService;
import com.infinite.framework.service.impl.MqttServiceImpl;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHandler;
import org.springframework.messaging.MessagingException;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

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
        mqttService.createConsumer(destination, new MessageHandler() {
            @Override
            public void handleMessage(Message<?> message) throws MessagingException {
                log.debug("recv text: {}", message);
            }
        });
        return "listen";
    }

    @RequestMapping("/jms/unlisten/{destination}")
    @ResponseBody
    public String unlistenTopic(@PathVariable("destination") String destination) {
        log.debug("unlisten topci : {}", destination);
        mqttService.removeConsumer(destination);
        return "unlisten";
    }

    @RequestMapping("/jms/topic")
    @ResponseBody
    public String sendToTopic() {
        for (int i = 0; i < 2; i++) {
            mqttService.sendTextMessage("test", "你好，生产者！这是消息：" + (i + 1));
        }
        return "success";
    }

    @RequestMapping("/jms/send")
    @ResponseBody
    public String sendToTopicDM(
            @ModelAttribute("destination") String destination,
            @ModelAttribute("message") String message) {
        destination = StringUtils.isEmpty(destination) ? "yinfantech/xgsn/jiaxing/control" : destination;
        message = StringUtils.isEmpty(message) ? "yinfantech/xgsn/jiaxing/control" : message;
        mqttService.sendTextMessage(destination, message);
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
