package com.infinite.eoa.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.infinite.eoa.core.entity.AbstractEntity;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * ComponentFN Entity Bean
 *
 * @author hx on 16-7-25.
 * @since 1.0
 */
@Embedded
public class Component extends AbstractEntity {
    public static final int INSTANCE_TYPE_MASTER_CONTROL = 4096;

    @Property private String comp_id;
    @Property private String name;
    @Property private String type;
    @Property private String ref_channel;
    @Property private int instance_type;
    @Property private int product_model;
    @Property private int category;
    @Property private int[] category_options;
    @Property private EntityConst.EntityStatus status = EntityConst.EntityStatus.NORMAL;
    @Embedded private ArrayList<Action> actions = new ArrayList<Action>(0);
    @Embedded private ArrayList<FieldDefinition> fieldDefinitions = new ArrayList<FieldDefinition>(0);
    @Embedded private ArrayList<DataFilterDefinetion> dataFilterDefinetions = new ArrayList<DataFilterDefinetion>(0);

    public String getComp_id() {
        return comp_id;
    }

    public void setComp_id(String comp_id) {
        this.comp_id = comp_id;
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

    @JsonIgnore
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

    @JsonIgnore
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

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    @JsonIgnore
    public int[] getCategory_options() {
        return category_options;
    }

    public void setCategory_options(int[] category_options) {
        this.category_options = category_options;
    }

    public int getInstance_type() {
        return instance_type;
    }

    public void setInstance_type(int instance_type) {
        this.instance_type = instance_type;
    }

    public int getProduct_model() {
        return product_model;
    }

    public void setProduct_model(int product_model) {
        this.product_model = product_model;
    }

    @Override
    public String toString() {
        return "Component{" +
                "comp_id='" + comp_id + '\'' +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", instance_type=" + instance_type +
                ", product_model=" + product_model +
                ", category=" + category +
                ", category_options=" + Arrays.toString(category_options) +
                ", status=" + status +
                ", actions=" + actions +
                ", fieldDefinitions=" + fieldDefinitions +
                ", dataFilterDefinetions=" + dataFilterDefinetions +
                '}';
    }
}
