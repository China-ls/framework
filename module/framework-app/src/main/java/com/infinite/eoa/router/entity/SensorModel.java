package com.infinite.eoa.router.entity;

import com.google.gson.reflect.TypeToken;
import com.infinite.eoa.core.entity.AbstractEntity;
import com.infinite.eoa.core.util.JsonUtil;
import com.infinite.eoa.core.web.BasicController;
import com.infinite.eoa.entity.Component;
import com.infinite.eoa.entity.VirtualSensor;

import java.util.ArrayList;

public class SensorModel extends AbstractEntity {
    private float longitude;
    private float latitude;
    private String name;
    private String type;
    private String dayDealDirtyWaterAbility;
    private String sim;
    private int internal_id;
    private String admin;
    private String contact;
    private String address;
    private String desc;
    private String components;
    private String setupDate;

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDayDealDirtyWaterAbility() {
        return dayDealDirtyWaterAbility;
    }

    public void setDayDealDirtyWaterAbility(String dayDealDirtyWaterAbility) {
        this.dayDealDirtyWaterAbility = dayDealDirtyWaterAbility;
    }

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public int getInternal_id() {
        return internal_id;
    }

    public void setInternal_id(int internal_id) {
        this.internal_id = internal_id;
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

    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }


    public VirtualSensor convert() {
        VirtualSensor sensor = new VirtualSensor();
        sensor.setName(name);
        sensor.setAddress(address);
        sensor.setAdmin(admin);
        sensor.setApp_id(BasicController.APPKEY);
        sensor.setContact(contact);
        sensor.setControl("");
        sensor.setReport("");
        sensor.setData("");
        sensor.setSystem("");
        sensor.setDay_deal_water_ability(dayDealDirtyWaterAbility);
        sensor.setDesc(desc);
        sensor.setIdle_report(600);
        sensor.setOffline_report(300);
        sensor.setInternal_id(internal_id);
        sensor.setLatitude(latitude);
        sensor.setLongitude(longitude);
        sensor.setSetup_date(setupDate);
        sensor.setStation_type(type);

        ArrayList<Component> componentEnties =
                JsonUtil.fromJson(components, new TypeToken<ArrayList<Component>>(){}.getType());

        for (Component component : componentEnties) {
//            if (component.get)
        }

        sensor.setComponents(componentEnties);

        return sensor;
    }

    @Override
    public String toString() {
        return "SensorModel{" +
                "longitude=" + longitude +
                ", latitude=" + latitude +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", dayDealDirtyWaterAbility='" + dayDealDirtyWaterAbility + '\'' +
                ", sim='" + sim + '\'' +
                ", internal_id='" + internal_id + '\'' +
                ", admin='" + admin + '\'' +
                ", contact='" + contact + '\'' +
                ", address='" + address + '\'' +
                ", desc='" + desc + '\'' +
                ", components=" + components +
                '}';
    }

}
