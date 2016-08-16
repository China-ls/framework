package com.infinite.water.controller;

import com.infinite.water.core.controller.AbstractController;
import com.infinite.water.websocket.SystemWebSocketHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.socket.TextMessage;

/**
 * @author hx on 16-7-29.
 */
@RequestMapping("/callback")
@Controller("CallbackController")
public class CallbackController extends AbstractController {
    private static Logger log = LoggerFactory.getLogger(CallbackController.class);

    @Qualifier("webSocketHandler")
    @Autowired
    private SystemWebSocketHandler systemWebSocketHandler;

    @RequestMapping(value = "/jms", method = RequestMethod.POST)
    @ResponseBody
    public String jmsCallback(@ModelAttribute("message") String message) {
        log.debug("recieve callback message : {}", message);
        systemWebSocketHandler.sendMessageToUsers(new TextMessage(message));
        return "success";
    }


}
