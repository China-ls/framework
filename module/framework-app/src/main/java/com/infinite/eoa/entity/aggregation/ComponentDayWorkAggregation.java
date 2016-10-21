package com.infinite.eoa.entity.aggregation;

import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

@Entity
public class ComponentDayWorkAggregation {
    @Id
    private String comp_id;
}
