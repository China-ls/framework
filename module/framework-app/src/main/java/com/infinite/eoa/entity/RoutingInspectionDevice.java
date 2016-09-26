package com.infinite.eoa.entity;

import com.infinite.eoa.core.entity.AbstractEntity;
import org.mongodb.morphia.annotations.Embedded;

@Embedded
public class RoutingInspectionDevice extends AbstractEntity {
    @Override
    public String toString() {
        return "RoutingInspectionDevice{} " + super.toString();
    }
}