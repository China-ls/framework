package com.infinite.water.entity;

import org.springframework.beans.factory.annotation.Value;

public class ServerConfig {
    @Value("#{server.protocal}")
    private String protocal;
    @Value("#{server.ip}")
    private String ip;
    @Value("#{server.port}")
    private String port;
    @Value("#{server.context}")
    private String context;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }

    public String getServerUrl() {
        return String.format("%s://%s:%s/%s", protocal, ip, port, context);
    }
}
