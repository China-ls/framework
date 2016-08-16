package com.infinite.water.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

//@Configuration
//@EnableWebSocketMessageBroker
public class WebSocketConfig extends WebMvcConfigurerAdapter implements WebSocketConfigurer {

    @Autowired
    @Qualifier("webSocketHandler")
    private WebSocketHandler webSocketHandler;

    @Autowired
    @Qualifier("jsWebSocketHandler")
    private WebSocketHandler jsWebSocketHandler;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(webSocketHandler, "/ws");
        registry.addHandler(jsWebSocketHandler, "/ws/js").setAllowedOrigins("*").withSockJS();
    }

}
