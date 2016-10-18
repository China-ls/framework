package com.infinite.eoa.entity;

import com.infinite.eoa.core.entity.AbstractEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Application Entity Bean
 *
 * @author hx on 16-7-25.
 * @since 1.0
 */
@Entity(value = EntityConst.CollectionName.APPLICATION, noClassnameStored = true)
public class Application extends AbstractEntity {
    @Id private String id;
    @Property private String accountId;
    @Property private String name;
    @Property private String appkey;
    @Property private EntityConst.EntityStatus status = EntityConst.EntityStatus.NORMAL;
    @Property private ArrayList<VirtualSensor> sensors = new ArrayList<VirtualSensor>(0);

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAppkey() {
        return appkey;
    }

    public void setAppkey(String appkey) {
        this.appkey = appkey;
    }

    public EntityConst.EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityConst.EntityStatus status) {
        this.status = status;
    }

    public ArrayList<VirtualSensor> getSensors() {
        return sensors;
    }

    public void setSensors(ArrayList<VirtualSensor> sensors) {
        this.sensors = sensors;
    }

    public Application addSensors(VirtualSensor... sensors) {
        if (null == sensors) {
            return this;
        }
        for (VirtualSensor sensor : sensors) {
            this.sensors.add(sensor);
        }
        return this;
    }

    public Application addSensors(Collection<VirtualSensor> sensors) {
        if (null == sensors) {
            return this;
        }
        this.sensors.addAll(sensors);
        return this;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
