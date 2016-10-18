package com.infinite.eoa.websocket;

import com.infinite.eoa.service.MqttService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.ArrayList;

/**
 * @author by hx on 16-8-16.
 */
public class SystemWebSocketHandler implements WebSocketHandler, WebSocketMessageHandler {
    private Logger log = LoggerFactory.getLogger(SystemWebSocketHandler.class);

    private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();

    @Autowired
    private MqttService mqttService;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("ConnectionEstablished : {}", session);
        }
        users.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("ws handleMessage : {}", message.toString());
        }
        String text = ((TextMessage) message).getPayload();
        if (!StringUtils.equals(text, "heartbeat")) {
            mqttService.sendTextMessage("yinfantech/xgsn/jiaxing/control", text);
            if (log.isDebugEnabled()) {
                log.debug("send to mqtt [dest: {}, message: {}]",
                        "yinfantech/xgsn/jiaxing/control", message);
            }
        }
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if (log.isErrorEnabled()) {
            log.error("handleTransportError : {}", session, exception);
        }
        if (session.isOpen()) {
            session.close();
        }
        users.remove(session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        if (log.isDebugEnabled()) {
            log.debug("afterConnectionClosed : {}", session);
        }
        users.remove(session);
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void broadcastMessageToUsers(TextMessage message) {
        if (log.isDebugEnabled()) {
            log.debug("will broadcast message : {}", message.getPayload());
        }
        for (WebSocketSession user : users) {
            try {
                user.sendMessage(message);
            } catch (IOException e) {
                if (log.isErrorEnabled()) {
                    log.error("broadcast message to user : {} error", user);
                }
            }
        }
        if (log.isDebugEnabled()) {
            log.debug("finish broadcast message");
        }
    }

    @Override
    public void broadcaseMessage(String message) {
        broadcastMessageToUsers(new TextMessage(message));
    }
}
