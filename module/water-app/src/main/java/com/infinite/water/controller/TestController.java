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
        broadcastMessageToWs("{\"app_id\": \"aidiman/xgsn/yinfantech\",\"comp_id\": \"2\",\"instant\": 0,\"negtive_totoal\": 0,\"positive_total\": 4618.30908,\"status\": true,\"time\": \"2016-08-15T19:37:17.245085\",\"uuid\": \"59ec0ac4-2182-4960-9166-3ce62738ef99\"}");
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
