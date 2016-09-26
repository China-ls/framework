package com.infinite.eoa.entity;

import org.mongodb.morphia.annotations.Embedded;

import java.util.ArrayList;

public class EmployeeDutyLinePatrol extends EmployeeDuty {
    @Embedded
    private ArrayList<RoutingInspectionDevice> devices = new ArrayList<RoutingInspectionDevice>(0);

    public EmployeeDutyLinePatrol() {
        setType(TYPE_LINE_PATROL);
    }

    public ArrayList<RoutingInspectionDevice> getDevices() {
        return devices;
    }

    public void setDevices(ArrayList<RoutingInspectionDevice> devices) {
        this.devices = devices;
    }

    public void addDevice(RoutingInspectionDevice device) {
        this.devices.add(device);
    }

    @Override
    public String toString() {
        return "EmployeeDutyLinePatrol{" +
                "devices=" + devices +
                "} " + super.toString();
    }
}