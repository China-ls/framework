package com.infinite.eoa.router.entity;

import com.google.gson.reflect.TypeToken;
import com.infinite.eoa.core.entity.AbstractEntity;
import com.infinite.eoa.core.util.JsonUtil;
import com.infinite.eoa.core.web.BasicController;
import com.infinite.eoa.entity.Action;
import com.infinite.eoa.entity.Component;
import com.infinite.eoa.entity.FieldDefinition;
import com.infinite.eoa.entity.VirtualSensor;
import org.apache.commons.lang.StringUtils;

import java.util.ArrayList;

public class SensorModel extends AbstractEntity {
    private String sensor_id;
    private float longitude;
    private float latitude;
    private String name;
    private String icon;
    private String station_type;
    private String day_deal_water_ability;
    private String sim;
    private long internal_id;
    private String admin;
    private String contact;
    private String address;
    private String desc;
    private String components;
    private String setupDate;
    private String departmentId;

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

    public String getSim() {
        return sim;
    }

    public void setSim(String sim) {
        this.sim = sim;
    }

    public long getInternal_id() {
        return internal_id;
    }

    public void setInternal_id(long internal_id) {
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

    public String getSetupDate() {
        return setupDate;
    }

    public void setSetupDate(String setupDate) {
        this.setupDate = setupDate;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public VirtualSensor convert() {
        VirtualSensor sensor = new VirtualSensor();
        sensor.setSensor_id(sensor_id);
        sensor.setName(name);
        sensor.setIcon(icon);
        sensor.setAddress(address);
        sensor.setAdmin(admin);
        sensor.setApp_id(BasicController.APPKEY);
        sensor.setContact(contact);
        sensor.setControl("");
        sensor.setReport("");
        sensor.setData("");
        sensor.setSystem("");
        sensor.setDay_deal_water_ability(day_deal_water_ability);
        sensor.setDesc(desc);
        sensor.setIdle_report(600);
        sensor.setOffline_report(300);
        sensor.setInternal_id(internal_id);
        sensor.setLatitude(latitude);
        sensor.setLongitude(longitude);
        sensor.setSetup_date(setupDate);
        sensor.setStation_type(station_type);
        sensor.setDepartmentId(departmentId);

        ArrayList<Component> componentEnties =
                JsonUtil.fromJson(components, new TypeToken<ArrayList<Component>>() {
                }.getType());

        for (Component component : componentEnties) {
            String fd = null;
            String actions = null;
            if (component.getInstance_type() == 4098) {
                fd = FD_4098;
            } else if (component.getInstance_type() == 4100) {
                fd = FD_4100;
            } else if (component.getInstance_type() == 3) {
                actions = ACT_3;
                fd = FD_3;
            } else if (component.getInstance_type() == 4099) {
                fd = FD_4099;
            } else if (StringUtils.equals("analog_sensor", component.getType())) {
                fd = FD_analog_sensor;
            } else if (StringUtils.equals("status_sensor", component.getType())) {
                fd = FD_status_sensor;
            } else if (component.getInstance_type() == 4096) {
                fd = FD_status_sensor;
                actions = ACT_BUTTON;
            } else if (StringUtils.equals("plc_controller", component.getType())) {
                fd = FD_status_sensor;
                actions = ACT_BUTTON;
            } else if (StringUtils.equals("onboard_controller", component.getType())) {
                fd = FD_plc;
                actions = ACT_BUTTON;
            }
            if (null != fd) {
                ArrayList<FieldDefinition> fieldDefinitions = JsonUtil.fromJson(fd, new TypeToken<ArrayList<FieldDefinition>>() {
                }.getType());
                component.setFieldDefinitions(fieldDefinitions);
            }
            if (null != actions) {
                ArrayList<Action> actionList = JsonUtil.fromJson(actions, new TypeToken<ArrayList<Action>>() {
                }.getType());
                component.setActions(actionList);
            }
        }

        sensor.setComponents(componentEnties);

        return sensor;
    }

    @Override
    public String toString() {
        return "SensorModel{" +
                "sensor_id='" + sensor_id + '\'' +
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", name='" + name + '\'' +
                ", icon='" + icon + '\'' +
                ", station_type='" + station_type + '\'' +
                ", day_deal_water_ability='" + day_deal_water_ability + '\'' +
                ", sim='" + sim + '\'' +
                ", internal_id=" + internal_id +
                ", admin='" + admin + '\'' +
                ", contact='" + contact + '\'' +
                ", address='" + address + '\'' +
                ", desc='" + desc + '\'' +
                ", components='" + components + '\'' +
                ", setupDate='" + setupDate + '\'' +
                ", departmentId='" + departmentId + '\'' +
                '}';
    }

    private static final String FD_4098 = "[{\"name\" : \"status\",\"type\" : \"boolean\"},{\"name\" : \"instant\",\"type\" : \"float\",\"unit\" : \"m3/s\"},{\"name\" : \"positive_total\",\"type\" : \"float\",\"unit\" : \"m3\"},{\"name\" : \"negtive_totoal\",\"type\" : \"float\",\"unit\" : \"m3\"},{\"name\" : \"time\",\"type\" : \"isodate\"}]";
    private static final String FD_4100 = "[{\"name\" : \"status\",\"type\" : \"boolean\"},{\"name\" : \"card_id\",\"type\" : \"int\"},{\"name\" : \"time\",\"type\" : \"isodate\"}]";
    private static final String FD_3 = "[{\"name\" : \"status\",\"type\" : \"boolean\"},{\"name\" : \"image\",\"type\" : \"image_data\"},{\"name\" : \"time\",\"type\" : \"isodate\"}]";
    private static final String ACT_3 = "[{\"channel_id\" : \"%2%\",\"operation\" : \"take_photo\",\"param\" : \"%3%\",\"sensor_id\" : \"%1%\"}]";
    private static final String FD_4099 = "[{\"name\" : \"status\",\"type\" : \"boolean\"},{\"name\" : \"power\",\"type\" : \"float\",\"unit\" : \"kWH\"},{\"name\" : \"voltageA\",\"type\" : \"float\",\"unit\" : \"V\"},{\"name\" : \"voltageB\",\"type\" : \"float\",\"unit\" : \"V\"},{\"name\" : \"voltageC\",\"type\" : \"float\",\"unit\" : \"V\"},{\"name\" : \"currentA\",\"type\" : \"float\",\"unit\" : \"A\"},{\"name\" : \"currentB\",\"type\" : \"float\",\"unit\" : \"A\"},{\"name\" : \"currentC\",\"type\" : \"float\",\"unit\" : \"A\"},{\"name\" : \"time\",\"type\" : \"isodate\"},{\"name\" : \"lostPowerA\",\"type\" : \"boolean\"},{\"name\" : \"lostPowerB\",\"type\" : \"boolean\"},{\"name\" : \"lostPowerC\",\"type\" : \"boolean\"},{\"name\" : \"errorVoltageOrder\",\"type\" : \"boolean\"},{\"name\" : \"errorCurrentOrder\",\"type\" : \"boolean\"},{\"name\" : \"temperature\",\"type\" : \"double\",\"unit\" : \"C\"}]";
    private static final String FD_analog_sensor = "[{\"name\" : \"value\",\"type\" : \"double\"},{\"name\" : \"time\",\"type\" : \"isodate\"}]";
    private static final String FD_status_sensor = "[{\"name\" : \"onoff\",\"type\" : \"boolean\"},{\"name\" : \"time\",\"type\" : \"isodate\"}]";
    private static final String FD_plc = "[{\"name\" : \"status\",\"type\" : \"boolean\"},{\"name\" : \"onoff\",\"type\" : \"boolean\"},{\"name\" : \"time\",\"type\" : \"isodate\"}]";
    private static final String ACT_BUTTON = "[{\"channel_id\" : \"%2%\",\"operation\" : \"switch\",\"param\" : \"%3%\",\"sensor_id\" : \"%1%\"}]";

}
