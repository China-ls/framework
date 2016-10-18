package com.infinite.eoa.entity;

import com.infinite.eoa.core.entity.AbstractEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

@Entity(value = EntityConst.CollectionName.VIRTUALSENSOR_EVENT, noClassnameStored = true)
public class SensorEvent extends AbstractEntity {
    public static final int EVENT_LOGIN = 0;
    public static final int EVENT_LOGOUT = 3;

    @Id private ObjectId id;
    @Property String app_id;
    @Property int event;
    @Property String event_name;
    @Property String remote_endpoint;
    @Property String sensor_id;
    @Property int signal;//最大32 最小0
    @Property long time;
    @Property int version;

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

    public int getEvent() {
        return event;
    }

    public void setEvent(int event) {
        this.event = event;
    }

    public String getEvent_name() {
        return event_name;
    }

    public void setEvent_name(String event_name) {
        this.event_name = event_name;
    }

    public String getRemote_endpoint() {
        return remote_endpoint;
    }

    public void setRemote_endpoint(String remote_endpoint) {
        this.remote_endpoint = remote_endpoint;
    }

    public String getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(String sensor_id) {
        this.sensor_id = sensor_id;
    }

    public int getSignal() {
        return signal;
    }

    public void setSignal(int signal) {
        this.signal = signal;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
