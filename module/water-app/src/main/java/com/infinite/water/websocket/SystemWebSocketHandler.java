package com.infinite.water.websocket;

import com.infinite.water.core.util.HttpUtils;
import com.infinite.water.entity.ServerConfig;
import org.apache.http.message.BasicNameValuePair;
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
import java.util.Date;

/**
 * @author by hx on 16-8-16.
 */
public class SystemWebSocketHandler implements WebSocketHandler , WebSocketMessageHandler {
    private Logger log = LoggerFactory.getLogger(SystemWebSocketHandler.class);

    private static final ArrayList<WebSocketSession> users = new ArrayList<WebSocketSession>();

    @Autowired
    private ServerConfig serverConfig;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        System.out.println("ConnectionEstablished");
        log.debug("ConnectionEstablished");
        users.add(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println("handleMessage" + message.toString());
        log.debug("handleMessage" + message.toString());
        HttpUtils.post(serverConfig.getServerUrl() + "/test/jms/send",
                new BasicNameValuePair("destination", "yinfantech/xgsn/jiaxing/control"),
                new BasicNameValuePair("message", ((TextMessage) message).getPayload()));
        session.sendMessage(new TextMessage(new Date() + ""));
    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        if(session.isOpen()){
            session.close();
        }
        users.remove(session);
        log.debug("handleTransportError" + exception.getMessage());
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        users.remove(session);
        log.debug("afterConnectionClosed" + closeStatus.getReason());
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    /**
     * 给所有在线用户发送消息
     * @param message
     */
    public void sendMessageToUsers(TextMessage message) {
        log.debug("will send message to ws users");
        for (WebSocketSession user : users) {
            try {
                if (user.isOpen()) {
                    user.sendMessage(message);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.debug("finish send message to ws users");
    }

    @Override
    public void broadcaseMessage(String message) {
        sendMessageToUsers(new TextMessage(message));
    }
}
