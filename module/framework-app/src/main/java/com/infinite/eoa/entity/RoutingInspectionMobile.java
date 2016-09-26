package com.infinite.eoa.entity;

import org.mongodb.morphia.annotations.Embedded;

import java.util.Date;

@Embedded
public class RoutingInspectionMobile extends RoutingInspectionDevice {
    private String name;
    private String model;
    private String channel;
    private String deviceid;
    private String sim;
    private Date createTime;
    private Date modifyTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getDeviceid() {
        return deviceid;
    }

    public void setDeviceid(String deviceid) {
        this.deviceid = deviceid;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Override
    public String toString() {
        return "RoutingInspectionMobile{" +
                "name='" + name + '\'' +
                ", model='" + model + '\'' +
                ", channel='" + channel + '\'' +
                ", deviceid='" + deviceid + '\'' +
                ", sim='" + sim + '\'' +
                ", createTime=" + createTime +
                ", modifyTime=" + modifyTime +
                "} ";
    }
}