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
    @Value("#{server.appkey}")
    private String appkey;
    @Value("#{server.namespace_employee_department}")
    private String namespaceEmployeeDepartment;
    @Value("#{server.namespace_employee}")
    private String namespaceEmployee;

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

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public String getNamespaceEmployeeDepartment() {
        return namespaceEmployeeDepartment;
    }

    public void setNamespaceEmployeeDepartment(String namespaceEmployeeDepartment) {
        this.namespaceEmployeeDepartment = namespaceEmployeeDepartment;
    }

    public String getNamespaceEmployee() {
        return namespaceEmployee;
    }

    public void setNamespaceEmployee(String namespaceEmployee) {
        this.namespaceEmployee = namespaceEmployee;
    }

    public String getServerUrl() {
        return String.format("%s://%s:%s/%s", protocal, ip, port, context);
    }
}
