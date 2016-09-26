package com.infinite.eoa.entity;

import com.infinite.eoa.core.entity.AbstractEntity;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Application Entity Bean
 *
 * @author hx on 16-7-25.
 * @since 1.0
 */
@Entity(EntityConst.CollectionName.APPLICATION)
public class Application extends AbstractEntity {
    @Id private String id;
    @Property private String accountId;
    @Property private String name;
    @Property private String appkey;
    @Property private EntityConst.EntityStatus status = EntityConst.EntityStatus.NORMAL;
    @Property private ArrayList<VirtualSensor> sensors = new ArrayList<VirtualSensor>(0);
    @Property private HashMap<String, Object> fields = new HashMap<String, Object>(0);

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

    public HashMap<String, Object> getFields() {
        return fields;
    }

    public void setFields(HashMap<String, Object> fields) {
        this.fields = fields;
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

    public Application put(String key, Object value) {
        if (null == key || null == value) {
            return this;
        }
        if (!key.startsWith(EntityConst.FieldName.ApplicationFN.PREFIX)) {
            key = EntityConst.FieldName.ApplicationFN.PREFIX + key;
        }
        fields.put(key, value);
        return this;
    }

    public <T> Object get(String key) {
        if (null == key) {
            return this;
        }
        if (!key.startsWith(EntityConst.FieldName.ApplicationFN.PREFIX)) {
            key = EntityConst.FieldName.ApplicationFN.PREFIX + key;
        }
        return fields.get(key);
    }

    public Application put(Map<String, Object> values) {
        if (null == values) {
            return this;
        }
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    @Override
    public String toString() {
        return "Application{" +
                "id='" + id + '\'' +
                ", accountId='" + accountId + '\'' +
                ", name='" + name + '\'' +
                ", appkey='" + appkey + '\'' +
                ", status=" + status +
                ", sensors=" + sensors +
                ", fields=" + fields +
                '}';
    }
}
