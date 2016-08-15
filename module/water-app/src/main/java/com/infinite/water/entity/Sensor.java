package com.infinite.water.entity;

import java.util.List;

public class Sensor {
    private String app_id;
    private String desc;
    private int idle_report;
    private long internal_id;
    private double lat;
    private double lon;
    private String name;
    private int offline_report;
    private String uuid;
    private List<Component> components;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getIdle_report() {
        return idle_report;
    }

    public void setIdle_report(int idle_report) {
        this.idle_report = idle_report;
    }

    public long getInternal_id() {
        return internal_id;
    }

    public void setInternal_id(long internal_id) {
        this.internal_id = internal_id;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOffline_report() {
        return offline_report;
    }

    public void setOffline_report(int offline_report) {
        this.offline_report = offline_report;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }
}
