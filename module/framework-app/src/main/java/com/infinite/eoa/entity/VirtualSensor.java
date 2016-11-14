package com.infinite.eoa.entity;

import com.infinite.eoa.core.entity.AbstractEntity;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

import java.util.ArrayList;
import java.util.Collection;

/**
 * VituralSensor Entity Bean
 *
 * @author hx on 16-7-25.
 * @since 1.0
 */
@Entity(value = EntityConst.CollectionName.VIRTUALSENSOR, noClassnameStored = true)
public class VirtualSensor extends AbstractEntity {
    public static final int TYPE_STATION = 100;

    @Id
    private String sensor_id;

    @Property
    private String app_id;
    @Property
    private String icon;
    @Property
    private String control;
    @Property
    private String report;
    @Property
    private String data;
    @Property
    private String system;

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
    private int online;// 1在线 , 2离线, 3 在线但是无数据

    @Property
    private String departmentName;
    @Property
    private String departmentId;
    @Reference
    private Department department;
    @Property
    private int status = EntityConst.EntityStatus.NORMAL.val;
    @Embedded
    private ArrayList<Component> components = new ArrayList<Component>(0);

    public String getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(String sensor_id) {
        this.sensor_id = sensor_id;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getIcon() {
        return icon;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getControl() {
        return control;
    }

    public void setControl(String control) {
        this.control = control;
    }

    public String getReport() {
        return report;
    }

    public void setReport(String report) {
        this.report = report;
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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
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

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(EntityConst.EntityStatus status) {
        this.status = status.val;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }

    public int getOnline() {
        return online;
    }

    public ArrayList<Component> getComponents() {
        return components;
    }

    public void setComponents(ArrayList<Component> components) {
        this.components = components;
    }

    public void addComponents(Collection<Component> components) {
        if (null == components) {
            return;
        }
        this.components.addAll(components);
    }

    @Override
    public String toString() {
        return "VirtualSensor{" +
                "sensor_id='" + sensor_id + '\'' +
                ", app_id='" + app_id + '\'' +
                ", control='" + control + '\'' +
                ", report='" + report + '\'' +
                ", data='" + data + '\'' +
                ", system='" + system + '\'' +
                ", name='" + name + '\'' +
                ", station_type='" + station_type + '\'' +
                ", day_deal_water_ability='" + day_deal_water_ability + '\'' +
                ", setup_date='" + setup_date + '\'' +
                ", admin='" + admin + '\'' +
                ", contact='" + contact + '\'' +
                ", address='" + address + '\'' +
                ", desc='" + desc + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", idle_report=" + idle_report +
                ", internal_id=" + internal_id +
                ", offline_report=" + offline_report +
                ", signal=" + signal +
                ", version=" + version +
                ", online=" + online +
                ", departmentName='" + departmentName + '\'' +
                ", departmentId='" + departmentId + '\'' +
                ", department=" + department +
                ", status=" + status +
                ", components=" + components +
                "} ";
    }
}
