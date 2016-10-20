package com.infinite.eoa.entity;

import com.google.gson.annotations.JsonAdapter;
import com.infinite.eoa.core.entity.AbstractEntity;
import com.infinite.eoa.core.gson.seri.SecondDateAdapter;
import org.mongodb.morphia.annotations.Embedded;

import java.util.Date;

@Embedded
public class SensorRefrenceStatusData extends AbstractEntity {
    private String comp_id;
    private String comp_type;
    @JsonAdapter(SecondDateAdapter.class)
    private Date time;
    private float value;

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

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }
}
