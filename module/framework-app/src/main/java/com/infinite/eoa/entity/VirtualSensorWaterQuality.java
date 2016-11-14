package com.infinite.eoa.entity;

import com.infinite.eoa.core.entity.AbstractEntity;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.util.Date;

@Entity(value = EntityConst.CollectionName.VIRTUALSENSOR_DATA_QUALITY, noClassnameStored = true)
public class VirtualSensorWaterQuality extends AbstractEntity {
    @Id private ObjectId id;
    @Property private String app_id;
    @Property private String sensor_id;
    @Property private double nh3n_in;
    @Property private double nh3n_out;
    @Property private double total_phosphorus_in;
    @Property private double total_phosphorus_out;
    @Property private double cod_in;
    @Property private double cod_out;
    @Property private double ph_in;
    @Property private double ph_out;
    @Property private Date time;

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

    public String getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(String sensor_id) {
        this.sensor_id = sensor_id;
    }

    public double getNh3n_in() {
        return nh3n_in;
    }

    public void setNh3n_in(double nh3n_in) {
        this.nh3n_in = nh3n_in;
    }

    public double getNh3n_out() {
        return nh3n_out;
    }

    public void setNh3n_out(double nh3n_out) {
        this.nh3n_out = nh3n_out;
    }

    public double getTotal_phosphorus_in() {
        return total_phosphorus_in;
    }

    public void setTotal_phosphorus_in(double total_phosphorus_in) {
        this.total_phosphorus_in = total_phosphorus_in;
    }

    public double getTotal_phosphorus_out() {
        return total_phosphorus_out;
    }

    public void setTotal_phosphorus_out(double total_phosphorus_out) {
        this.total_phosphorus_out = total_phosphorus_out;
    }

    public double getCod_in() {
        return cod_in;
    }

    public void setCod_in(double cod_in) {
        this.cod_in = cod_in;
    }

    public double getCod_out() {
        return cod_out;
    }

    public void setCod_out(double cod_out) {
        this.cod_out = cod_out;
    }

    public double getPh_in() {
        return ph_in;
    }

    public void setPh_in(double ph_in) {
        this.ph_in = ph_in;
    }

    public double getPh_out() {
        return ph_out;
    }

    public void setPh_out(double ph_out) {
        this.ph_out = ph_out;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
