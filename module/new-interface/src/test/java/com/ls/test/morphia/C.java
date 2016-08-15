package com.ls.test.morphia;

import org.mongodb.morphia.annotations.Embedded;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;

import java.util.HashMap;

/**
 * Created by hx on 16-8-2.
 */
@Embedded
public class C {
    @Id
    @Property
    String id;
    @Property
    String name;
    @Property
    HashMap<String, String> fields = new HashMap<String, String>(0);

    public C() {
    }

    public C(String id, String name, HashMap<String, String> fields) {
        this.id = id;
        this.name = name;
        this.fields = fields;
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

    public HashMap<String, String> getFields() {
        return fields;
    }

    public void setFields(HashMap<String, String> fields) {
        this.fields = fields;
    }

    @Override
    public String toString() {
        return "C{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", fields=" + fields +
                '}';
    }
}
