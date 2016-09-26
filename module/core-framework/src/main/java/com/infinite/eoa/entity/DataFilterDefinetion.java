package com.infinite.eoa.entity;

import com.infinite.eoa.core.entity.AbstractEntity;
import org.bson.Document;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

/**
 * ComponentFN Entity Bean
 *
 * @author hx on 16-8-16.
 * @since 1.0
 */
@Embedded
public class DataFilterDefinetion extends AbstractEntity {
    @Property
    private String id;
    @Property
    private CallbackType callbackType;
    @Property
    private String callbackDestination;
    @Property
    private Document filterConfig;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public CallbackType getCallbackType() {
        return callbackType;
    }

    public void setCallbackType(CallbackType callbackType) {
        this.callbackType = callbackType;
    }

    public String getCallbackDestination() {
        return callbackDestination;
    }

    public void setCallbackDestination(String callbackDestination) {
        this.callbackDestination = callbackDestination;
    }

    public Document getFilterConfig() {
        return filterConfig;
    }

    public void setFilterConfig(Document filterConfig) {
        this.filterConfig = filterConfig;
    }

    @Override
    public String toString() {
        return "DataFilterDefinetion{" +
                "id='" + id + '\'' +
                ", callbackType=" + callbackType +
                ", callbackDestination='" + callbackDestination + '\'' +
                ", filterConfig=" + filterConfig +
                '}';
    }
}
