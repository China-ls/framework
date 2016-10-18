package com.infinite.eoa.entity;

import com.google.gson.annotations.JsonAdapter;
import com.infinite.eoa.core.entity.AbstractEntity;
import com.infinite.eoa.core.gson.seri.SecondDateAdapter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.util.Date;

@Entity(value = EntityConst.CollectionName.VIRTUALSENSOR_DATA, noClassnameStored = true)
public class VirtualSensorData extends AbstractEntity {
    @Id private ObjectId id;

    @Property private String app_id;
    @Property private String comp_id;
    @Property private String comp_type;
    @Property private String sensor_id;
    @Property private boolean status;
    @JsonAdapter(SecondDateAdapter.class)
    @Property private Date time;
    @Property private float currentA;
    @Property private float currentB;
    @Property private float currentC;
    @Property private float power;
    @Property private float voltageA;
    @Property private float voltageB;
    @Property private float voltageC;
    @Property private float value0;
    @Property private float value2;
    @Property private float value3;
    @Property private float value4;
    @Property private float value5;
    @Property private float value6;
    @Property private float value7;
    @Property private boolean onoff;

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getComp_id() {
        return comp_id;
    }

    public void setComp_id(String comp_id) {
        this.comp_id = comp_id;
    }

    public String getComp_type() {
        return comp_type;
    }

    public void setComp_type(String comp_type) {
        this.comp_type = comp_type;
    }

    public String getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(String sensor_id) {
        this.sensor_id = sensor_id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public float getCurrentA() {
        return currentA;
    }

    public void setCurrentA(float currentA) {
        this.currentA = currentA;
    }

    public float getCurrentB() {
        return currentB;
    }

    public void setCurrentB(float currentB) {
        this.currentB = currentB;
    }

    public float getCurrentC() {
        return currentC;
    }

    public void setCurrentC(float currentC) {
        this.currentC = currentC;
    }

    public float getPower() {
        return power;
    }

    public void setPower(float power) {
        this.power = power;
    }

    public float getVoltageA() {
        return voltageA;
    }

    public void setVoltageA(float voltageA) {
        this.voltageA = voltageA;
    }

    public float getVoltageB() {
        return voltageB;
    }

    public void setVoltageB(float voltageB) {
        this.voltageB = voltageB;
    }

    public float getVoltageC() {
        return voltageC;
    }

    public void setVoltageC(float voltageC) {
        this.voltageC = voltageC;
    }

    public float getValue0() {
        return value0;
    }

    public void setValue0(float value0) {
        this.value0 = value0;
    }

    public float getValue2() {
        return value2;
    }

    public void setValue2(float value2) {
        this.value2 = value2;
    }

    public float getValue3() {
        return value3;
    }

    public void setValue3(float value3) {
        this.value3 = value3;
    }

    public float getValue4() {
        return value4;
    }

    public void setValue4(float value4) {
        this.value4 = value4;
    }

    public float getValue5() {
        return value5;
    }

    public void setValue5(float value5) {
        this.value5 = value5;
    }

    public float getValue6() {
        return value6;
    }

    public void setValue6(float value6) {
        this.value6 = value6;
    }

    public float getValue7() {
        return value7;
    }

    public void setValue7(float value7) {
        this.value7 = value7;
    }

    public boolean isOnoff() {
        return onoff;
    }

    public void setOnoff(boolean onoff) {
        this.onoff = onoff;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
