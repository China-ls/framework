package com.infinite.framework.entity;

import com.infinite.framework.core.entity.AbstractEntity;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

/**
 * ActionFN Entity Bean
 *
 * @author hx on 16-7-25.
 * @since 1.0
 */
@Embedded
public class Action extends AbstractEntity {
    @Property
    private String id;
    @Property
    private String channel_id;
    @Property
    private String operation;
    @Property
    private String param;
    @Property
    private String sensor_id;
    @Property
    private EntityConst.EntityStatus status = EntityConst.EntityStatus.NORMAL;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getChannel_id() {
        return channel_id;
    }

    public void setChannel_id(String channel_id) {
        this.channel_id = channel_id;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getSensor_id() {
        return sensor_id;
    }

    public void setSensor_id(String sensor_id) {
        this.sensor_id = sensor_id;
    }

    public EntityConst.EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityConst.EntityStatus status) {
        this.status = status;
    }
}
