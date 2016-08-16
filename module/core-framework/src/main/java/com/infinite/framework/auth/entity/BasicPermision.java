package com.infinite.framework.auth.entity;

import org.bson.Document;

/**
 * Created by hx on 16-7-5.
 */
public class BasicPermision implements IPermission {
    protected String name;
    protected String describtion;
    protected boolean isDisable;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescribtion() {
        return describtion;
    }

    public void setDescribtion(String describtion) {
        this.describtion = describtion;
    }

    public boolean isDisable() {
        return isDisable;
    }

    public void setDisable(boolean disable) {
        isDisable = disable;
    }

    public Document toDocument() {
        return new Document()
                .append("name", name)
                .append("describtion", describtion)
                .append("isDisable", isDisable);
    }

    public void fromDocument(Document document) {
        setName(document.getString("name"));
        setDescribtion(document.getString("describtion"));
        setDisable(document.getBoolean("isDisable"));
    }

    public String stringFormat() {
        return null;
    }

    public void fromString(String string) {

    }
}
