package com.infinite.eoa.entity;

import com.google.gson.annotations.JsonAdapter;
import com.infinite.eoa.core.entity.AbstractEntity;
import com.infinite.eoa.core.gson.seri.SecondDateAdapter;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Embedded;
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
    @Property private String image;
    @Property private String capture_type;
    @Property private String image_id;
    @Property private boolean status;
    @JsonAdapter(SecondDateAdapter.class)
    @Property private Date time;
    @Property private double instant;
    @Property private double positive_total;
    @Property private double currentA;
    @Property private double currentB;
    @Property private double currentC;
    @Property private double power;
    @Property private double voltageA;
    @Property private double voltageB;
    @Property private double voltageC;
    @Property private double value;
    @Property private double value0;
    @Property private double value2;
    @Property private double value3;
    @Property private double value4;
    @Property private double value5;
    @Property private double value6;
    @Property private double value7;
    @Property private boolean onoff;
    @Embedded private SensorRefrenceStatusData ref_status;

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

    public double getCurrentA() {
        return currentA;
    }

    public void setCurrentA(double currentA) {
        this.currentA = currentA;
    }

    public double getCurrentB() {
        return currentB;
    }

    public void setCurrentB(double currentB) {
        this.currentB = currentB;
    }

    public double getCurrentC() {
        return currentC;
    }

    public void setCurrentC(double currentC) {
        this.currentC = currentC;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public double getVoltageA() {
        return voltageA;
    }

    public void setVoltageA(double voltageA) {
        this.voltageA = voltageA;
    }

    public double getVoltageB() {
        return voltageB;
    }

    public void setVoltageB(double voltageB) {
        this.voltageB = voltageB;
    }

    public double getVoltageC() {
        return voltageC;
    }

    public void setVoltageC(double voltageC) {
        this.voltageC = voltageC;
    }

    public double getValue0() {
        return value0;
    }

    public void setValue0(double value0) {
        this.value0 = value0;
    }

    public double getValue2() {
        return value2;
    }

    public void setValue2(double value2) {
        this.value2 = value2;
    }

    public double getValue3() {
        return value3;
    }

    public void setValue3(double value3) {
        this.value3 = value3;
    }

    public double getValue4() {
        return value4;
    }

    public void setValue4(double value4) {
        this.value4 = value4;
    }

    public double getValue5() {
        return value5;
    }

    public void setValue5(double value5) {
        this.value5 = value5;
    }

    public double getValue6() {
        return value6;
    }

    public void setValue6(double value6) {
        this.value6 = value6;
    }

    public double getValue7() {
        return value7;
    }

    public void setValue7(double value7) {
        this.value7 = value7;
    }

    public boolean isOnoff() {
        return onoff;
    }

    public void setOnoff(boolean onoff) {
        this.onoff = onoff;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCapture_type() {
        return capture_type;
    }

    public void setCapture_type(String capture_type) {
        this.capture_type = capture_type;
    }

    public String getImage_id() {
        return image_id;
    }

    public void setImage_id(String image_id) {
        this.image_id = image_id;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public SensorRefrenceStatusData getRef_status() {
        return ref_status;
    }

    public void setRef_status(SensorRefrenceStatusData ref_status) {
        this.ref_status = ref_status;
    }

    public double getInstant() {
        return instant;
    }

    public void setInstant(double instant) {
        this.instant = instant;
    }

    public double getPositive_total() {
        return positive_total;
    }

    public void setPositive_total(double positive_total) {
        this.positive_total = positive_total;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
