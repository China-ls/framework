package com.infinite.eoa.entity.cencus;

import com.infinite.eoa.entity.EntityConst;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Property;

@Entity(value = EntityConst.CollectionName.CENCUS_COMPONENT_DAY_WORK, noClassnameStored = true)
public class ComponentDayWorkCencus extends DayCencusEntity {
    @Property private String comp_id;
    @Property private long work;

    public String getComp_id() {
        return comp_id;
    }

    public void setComp_id(String comp_id) {
        this.comp_id = comp_id;
    }

    public long getWork() {
        return work;
    }

    public void setWork(long work) {
        this.work = work;
    }

    @Override
    public String toString() {
        return "ComponentDayWorkCencus{" +
                " comp_id='" + comp_id + '\'' +
                ", work=" + work +
                "} " + super.toString();
    }
}
