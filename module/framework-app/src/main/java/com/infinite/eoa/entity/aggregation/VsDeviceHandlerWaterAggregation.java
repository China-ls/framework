package com.infinite.eoa.entity.aggregation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.bson.Document;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

@Entity
public class VsDeviceHandlerWaterAggregation {
    @Id
    private String sensor_id;
    @Property
    private String name;
    @Property
    private String station_type;
    @Property
    private String day_deal_water_ability;
    @Property
    private String setup_date;
    @Property
    private String admin;
    @Property
    private String contact;
    @Property
    private String address;
    @Property
    private String desc;
    @Property
    private double latitude;
    @Property
    private double longitude;
    @Property
    private long idle_report;
    @Property
    private long internal_id;
    @Property
    private long offline_report;
    @Property
    private int signal;
    @Property
    private int version;
    @Property
    private int online;
    @Property
    private String departmentName;
    private String id;

    private String handlerWater;
    private String electric;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void convertId() {
        if (null == sensor_id) {
            return;
        }
        try {
            this.id = Document.parse(sensor_id).getString("sensor_id");
        } catch (Exception e) {
        }
    }

    @JsonIgnore
    public String getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(String sensor_id) {
        this.sensor_id = sensor_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStation_type() {
        return station_type;
    }

    public void setStation_type(String station_type) {
        this.station_type = station_type;
    }

    public String getDay_deal_water_ability() {
        return day_deal_water_ability;
    }

    public void setDay_deal_water_ability(String day_deal_water_ability) {
        this.day_deal_water_ability = day_deal_water_ability;
    }

    public String getSetup_date() {
        return setup_date;
    }

    public void setSetup_date(String setup_date) {
        this.setup_date = setup_date;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public long getIdle_report() {
        return idle_report;
    }

    public void setIdle_report(long idle_report) {
        this.idle_report = idle_report;
    }

    public long getInternal_id() {
        return internal_id;
    }

    public void setInternal_id(long internal_id) {
        this.internal_id = internal_id;
    }

    public long getOffline_report() {
        return offline_report;
    }

    public void setOffline_report(long offline_report) {
        this.offline_report = offline_report;
    }

    public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getHandlerWater() {
        return handlerWater;
    }

    public void setHandlerWater(String handlerWater) {
        this.handlerWater = handlerWater;
    }

    public String getElectric() {
        return electric;
    }

    public void setElectric(String electric) {
        this.electric = electric;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
