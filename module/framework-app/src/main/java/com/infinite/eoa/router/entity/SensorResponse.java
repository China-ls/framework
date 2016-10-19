package com.infinite.eoa.router.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.infinite.eoa.core.entity.AbstractEntity;
import com.infinite.eoa.entity.Component;
import com.infinite.eoa.entity.VirtualSensor;
import com.infinite.eoa.entity.VirtualSensorData;

import java.util.ArrayList;

/**
 * @author by hx on 16-7-6.
 */
public class SensorResponse extends AbstractEntity {
    private VirtualSensor sensor;
    private Component masterControl;
    private ArrayList<Component> freeComp;
    private ArrayList<Component> controlComp;
    private ArrayList<Component> statusComp;
    private ArrayList<Component> refrenceComp;
    private ArrayList<VirtualSensorData> data;

    public VirtualSensor getSensor() {
        return sensor;
    }

    public void setSensor(VirtualSensor sensor) {
        this.sensor = sensor;
    }

    public ArrayList<VirtualSensorData> getData() {
        return data;
    }

    public void setData(ArrayList<VirtualSensorData> data) {
        this.data = data;
    }

    @JsonProperty("mc")
    public Component getMasterControl() {
        return masterControl;
    }

    public void setMasterControl(Component masterControl) {
        this.masterControl = masterControl;
    }

    @JsonProperty("fc")
    public ArrayList<Component> getFreeComp() {
        return freeComp;
    }

    public void setFreeComp(ArrayList<Component> freeComp) {
        this.freeComp = freeComp;
    }

    public void addFreeComp(Component comp) {
        if (null == this.freeComp) {
            this.freeComp = new ArrayList<Component>();
        }
        this.freeComp.add(comp);
    }

    @JsonProperty("cc")
    public ArrayList<Component> getControlComp() {
        return controlComp;
    }

    public void setControlComp(ArrayList<Component> controlComp) {
        this.controlComp = controlComp;
    }

    public void addControlComp(Component comp) {
        if (null == this.controlComp) {
            this.controlComp = new ArrayList<Component>();
        }
        this.controlComp.add(comp);
    }

    @JsonProperty("sc")
    public ArrayList<Component> getStatusComp() {
        return statusComp;
    }

    public void setStatusComp(ArrayList<Component> statusComp) {
        this.statusComp = statusComp;
    }

    public void addStatusComp(Component comp) {
        if (null == this.statusComp) {
            this.statusComp = new ArrayList<Component>();
        }
        this.statusComp.add(comp);
    }

    @JsonProperty("rc")
    public ArrayList<Component> getRefrenceComp() {
        return refrenceComp;
    }

    public void setRefrenceComp(ArrayList<Component> refrenceComp) {
        this.refrenceComp = refrenceComp;
    }

    public void addRefrenceComp(Component comp) {
        if (null == this.refrenceComp) {
            this.refrenceComp = new ArrayList<Component>();
        }
        this.refrenceComp.add(comp);
    }

    public void classifyComponents() {
        if (null != sensor) {
            ArrayList<Component> comps = sensor.getComponents();
            for (Component component : comps) {
                switch (component.getCategory()) {
                    case 0://自由类型
                        addFreeComp(component);
                        break;
                    case 1://动力控制类型
                        if (component.getInstance_type() == Component.INSTANCE_TYPE_MASTER_CONTROL) {
                            this.masterControl = component;
                        } else {
                            addControlComp(component);
                        }
                        break;
                    case 2://状态类型
                        addStatusComp(component);
                        break;
                    case 3://参考状态类型
                        addRefrenceComp(component);
                        break;
                }
            }
        }
    }

}