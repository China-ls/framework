package com.infinite.water.controller;

import com.infinite.water.core.controller.AbstractController;
import com.infinite.water.websocket.WebSocketMessageHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author hx on 16-7-29.
 */
@RequestMapping("/test")
@Controller("TestController")
public class TestController extends AbstractController {
    private static Logger log = LoggerFactory.getLogger(TestController.class);

    @Qualifier("webSocketHandler")
    @Autowired
    private WebSocketMessageHandler webSocketHandler;

    @RequestMapping(value = "/ws/send/data")
    @ResponseBody
    public String sendData() {
        broadcastMessageToWs("[{\"app_id\":\"aidiman/xgsn/yinfantech\",\"comp_id\":\"1:1\",\"comp_type\":\"plc_controller\",\"onoff\":false,\"sensor_id\":\"c81e39a5-359b-41c1-9cd4-6eaf6b6d5590\",\"status\":false,\"time\":1472004016762,\"_id\":\"dcd8b7f8139833a232ce12d04efba9e87b40b460\"},{\"app_id\":\"aidiman/xgsn/yinfantech\",\"comp_id\":\"1:2\",\"comp_type\":\"plc_controller\",\"onoff\":false,\"sensor_id\":\"c81e39a5-359b-41c1-9cd4-6eaf6b6d5590\",\"status\":false,\"time\":1472004016762,\"_id\":\"931f9690bb2c0465428bd250e7284fab7bc8d144\"},{\"app_id\":\"aidiman/xgsn/yinfantech\",\"comp_id\":\"2\",\"comp_type\":\"flowmeter_sensor\",\"instant\":0.0,\"negtive_totoal\":0.0,\"positive_total\":0.0,\"sensor_id\":\"c81e39a5-359b-41c1-9cd4-6eaf6b6d5590\",\"status\":false,\"time\":1472004016762,\"_id\":\"3563253beebc482399b6e9a4462f089845f2ecda\"},{\"app_id\":\"aidiman/xgsn/yinfantech\",\"comp_id\":\"3\",\"comp_type\":\"cardreader_sensor\",\"sensor_id\":\"c81e39a5-359b-41c1-9cd4-6eaf6b6d5590\",\"status\":false,\"time\":1472004016762,\"_id\":\"68b4de6d1313b47fed4859eac5cbec973ff72ec4\"},{\"app_id\":\"aidiman/xgsn/yinfantech\",\"comp_id\":\"4\",\"sensor_id\":\"c81e39a5-359b-41c1-9cd4-6eaf6b6d5590\",\"status\":false,\"time\":1472004016762,\"_id\":\"d2d41891fe2c567c9fc438beb696d60de0466855\",\"comp_type\":\"camera_controller\"},{\"app_id\":\"aidiman/xgsn/yinfantech\",\"comp_id\":\"5\",\"comp_type\":\"electricmeter_sensor\",\"currentA\":0.0,\"currentB\":0.0,\"currentC\":0.0,\"power\":0.0,\"sensor_id\":\"c81e39a5-359b-41c1-9cd4-6eaf6b6d5590\",\"status\":true,\"voltageA\":0.0,\"voltageB\":0.0,\"voltageC\":0.0,\"time\":1472004016762,\"_id\":\"f1d53dae534908c5d25a1bba93d0cb8cb363e77d\"},{\"app_id\":\"aidiman/xgsn/yinfantech\",\"comp_id\":\"6\",\"comp_type\":\"analog_sensor\",\"sensor_id\":\"c81e39a5-359b-41c1-9cd4-6eaf6b6d5590\",\"value0\":8.056640625E-4,\"value1\":8.056640625E-4,\"value2\":0.001611328125,\"value3\":0.001611328125,\"value4\":8.056640625E-4,\"value5\":8.056640625E-4,\"value6\":0.001611328125,\"value7\":0.001611328125,\"time\":1472004016763,\"_id\":\"f5057672ccba20dd9851e8c241e3b6604bac101c\"},{\"app_id\":\"aidiman/xgsn/yinfantech\",\"comp_id\":\"7\",\"comp_type\":\"status_sensor\",\"sensor_id\":\"c81e39a5-359b-41c1-9cd4-6eaf6b6d5590\",\"status\":false,\"time\":1472004016763,\"_id\":\"8cc8021582492e9d9ee8172d099a075cbda0c8f1\"},{\"app_id\":\"aidiman/xgsn/yinfantech\",\"comp_id\":\"8\",\"comp_type\":\"onboard_controller\",\"onoff\":false,\"sensor_id\":\"c81e39a5-359b-41c1-9cd4-6eaf6b6d5590\",\"time\":1472004016763,\"_id\":\"96f086b429f70f8050b41818da96d13f2d766f27\"}]");
        return "success";
    }

    @RequestMapping(value = "/ws/send/img")
    @ResponseBody
    public String sendImageData() {
        broadcastMessageToWs("{\"app_id\" : \"aidiman/xgsn/yinfantech\",\"comp_id\" : \"4\",\"time\" :  \"2016-08-15T19:37:17.245085\",\"image\" : \"data:image/jpeg;base64,AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA=\"}");
        return "success";
    }

    void broadcastMessageToWs(String message) {
        webSocketHandler.broadcaseMessage(message);
    }

}
