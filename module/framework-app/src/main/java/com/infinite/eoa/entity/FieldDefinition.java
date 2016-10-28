package com.infinite.eoa.entity;

import com.infinite.eoa.core.entity.AbstractEntity;
import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Property;

/**
 * FieldDefinition Entity Bean
 *
 * @author hx on 16-7-25.
 * @since 1.0
 */
@Embedded
public class FieldDefinition extends AbstractEntity {
    @Property private String name;
    @Property private String type;
    @Property private String unit;

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

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return "FieldDefinition{" +
                "name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", unit='" + unit + '\'' +
                '}';
    }
}
