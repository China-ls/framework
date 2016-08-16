package com.infinite.framework.entity;

import com.infinite.framework.core.entity.AbstractEntity;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

import java.util.ArrayList;
import java.util.Collection;

/**
 * ComponentFN Entity Bean
 *
 * @author hx on 16-7-25.
 * @since 1.0
 */
//@Entity(EntityConst.CollectionName.COMPONENT)
@Embedded
public class Component extends AbstractEntity {
    @Property
    private String id;
    @Property
    private String sensorId;
    @Property
    private String name;
    @Property
    private String type;
    @Property
    private EntityConst.EntityStatus status = EntityConst.EntityStatus.NORMAL;
    @Embedded
    private ArrayList<Action> actions = new ArrayList<Action>(0);
    @Embedded
    private ArrayList<FieldDefinition> fieldDefinitions = new ArrayList<FieldDefinition>(0);
    @Embedded
    private ArrayList<DataFilterDefinetion> dataFilterDefinetions = new ArrayList<DataFilterDefinetion>(0);

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSensorId() {
        return sensorId;
    }

    public void setSensorId(String sensorId) {
        this.sensorId = sensorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public EntityConst.EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityConst.EntityStatus status) {
        this.status = status;
    }

    public ArrayList<FieldDefinition> getFieldDefinitions() {
        return fieldDefinitions;
    }

    public void setFieldDefinitions(ArrayList<FieldDefinition> fieldDefinitions) {
        this.fieldDefinitions = fieldDefinitions;
    }

    public ArrayList<Action> getActions() {
        return actions;
    }

    public void setActions(ArrayList<Action> actions) {
        this.actions = actions;
    }

    public ArrayList<DataFilterDefinetion> getDataFilterDefinetions() {
        return dataFilterDefinetions;
    }

    public void setDataFilterDefinetions(ArrayList<DataFilterDefinetion> dataFilterDefinetions) {
        this.dataFilterDefinetions = dataFilterDefinetions;
    }

    public Component addActions(Action... actions) {
        if (null == actions) {
            return this;
        }
        for (Action action : actions) {
            this.actions.add(action);
        }
        return this;
    }

    public Component addActions(Collection<Action> actions) {
        if (null == actions) {
            return this;
        }
        this.actions.addAll(actions);
        return this;
    }

    @Override
    public String toString() {
        return "Component{" +
                "id='" + id + '\'' +
                ", sensorId='" + sensorId + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", status=" + status +
                ", actions=" + actions +
                ", fieldDefinitions=" + fieldDefinitions +
                '}';
    }
}
