package com.infinite.eoa.entity;

import com.infinite.eoa.core.entity.AbstractEntity;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * VituralSensor Entity Bean
 *
 * @author hx on 16-7-25.
 * @since 1.0
 */
@Entity(EntityConst.CollectionName.VIRTUALSENSOR)
public class VirtualSensor extends AbstractEntity {
    @Id private String sensor_id;
    @Property private String app_id;
    @Property private String name;
    @Property private String control;
    @Property private String report;
    @Property private String data;
    @Property private String system;
    @Property private String desc;
    @Property private long idle_report;
    @Property private long internal_id;
    @Property private double latitude;
    @Property private double longitude;
    @Property private String contact;
    @Property private String admin;
    @Property private String address;
    @Property private String station_type;
    @Property private long offline_report;
    @Property private EntityConst.EntityStatus status = EntityConst.EntityStatus.NORMAL;
    @Embedded private ArrayList<Component> components = new ArrayList<Component>(0);
    private HashMap<String, Object> fields = new HashMap<String, Object>(0);

    public String getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(String sensor_id) {
        this.sensor_id = sensor_id;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getSystem() {
        return system;
    }

    public void setSystem(String system) {
        this.system = system;
    }

    public EntityConst.EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityConst.EntityStatus status) {
        this.status = status;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
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

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAdmin() {
        return admin;
    }

    public void setAdmin(String admin) {
        this.admin = admin;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getStation_type() {
        return station_type;
    }

    public void setStation_type(String station_type) {
        this.station_type = station_type;
    }

    public long getOffline_report() {
        return offline_report;
    }

    public void setOffline_report(long offline_report) {
        this.offline_report = offline_report;
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<Component> components) {
        this.components = components;
    }

    public void addComponents(Component... components) {
        if (null == components) {
            return;
        }
        for (Component comp : components) {
            this.components.add(comp);
        }
    }

    public void addComponents(Collection<Component> components) {
        if (null == components) {
            return;
        }
        this.components.addAll(components);
    }

    public HashMap<String, Object> getFields() {
        return fields;
    }

    public void setFields(HashMap<String, Object> fields) {
        this.fields = fields;
    }

    public VirtualSensor put(String key, Object value) {
        fields.put(key, value);
        return this;
    }

    public <T> Object get(String key) {
        return fields.get(key);
    }

    public VirtualSensor put(Map<String, Object> values) {
        fields.putAll(values);
        return this;
    }

    @Override
    public String toString() {
        return "VirtualSensor{" +
                "sensor_id='" + sensor_id + '\'' +
                ", app_id='" + app_id + '\'' +
                ", name='" + name + '\'' +
                ", control='" + control + '\'' +
                ", report='" + report + '\'' +
                ", data='" + data + '\'' +
                ", system='" + system + '\'' +
                ", desc='" + desc + '\'' +
                ", idle_report=" + idle_report +
                ", internal_id=" + internal_id +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", contact='" + contact + '\'' +
                ", admin='" + admin + '\'' +
                ", address='" + address + '\'' +
                ", station_type='" + station_type + '\'' +
                ", offline_report=" + offline_report +
                ", status=" + status +
                ", components=" + components +
                ", fields=" + fields +
                '}';
    }
}
