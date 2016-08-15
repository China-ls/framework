package com.ls.test.morphia;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by hx on 16-8-2.
 */
@Entity(value = "temp")
public class V {
    @Id
    @Property
    String id;
    @Property
    String name;
    @Property
    HashMap<String, String> fields = new HashMap<String, String>(0);
    @Embedded ArrayList<C> cs = new ArrayList<C>(0);

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

    public HashMap<String, String> getFields() {
        return fields;
    }

    public void setFields(HashMap<String, String> fields) {
        this.fields = fields;
    }

    public ArrayList<C> getCs() {
        return cs;
    }

    public void setCs(ArrayList<C> cs) {
        this.cs = cs;
    }

    @Override
    public String toString() {
        return "V{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", fields=" + fields +
                ", cs=" + cs +
                '}';
    }
}
