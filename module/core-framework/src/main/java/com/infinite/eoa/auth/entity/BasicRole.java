package com.infinite.eoa.auth.entity;

import org.apache.commons.lang.ArrayUtils;
import org.bson.Document;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by hx on 16-7-5.
 */
public class BasicRole implements IRole {
    protected String name;
    protected String describtion;
    protected boolean isDisable;
    protected ArrayList<IPermission> permisionList = new ArrayList<IPermission>(0);

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

    public void addPermission(IPermission permision) {
        if (!permisionList.contains(permision)) {
            permisionList.add(permision);
        }
    }

    public void removePermission(IPermission permision) {
        if (permisionList.contains(permision)) {
            permisionList.remove(permision);
        }
    }

    public void addPermissions(IPermission... permisions) {
        if (ArrayUtils.isEmpty(permisions)) {
            return;
        }
        for (IPermission permision : permisions) {
            if (!permisionList.contains(permision)) {
                permisionList.add(permision);
            }
        }
    }

    public void removePermissions(IPermission... permisions) {
        if (ArrayUtils.isEmpty(permisions)) {
            return;
        }
        for (IPermission permision : permisions) {
            if (permisionList.contains(permision)) {
                permisionList.remove(permision);
            }
        }
    }

    public void addPermissions(List<IPermission> permisions) {
        if (null == permisions || permisions.size() <= 0) {
            return;
        }
        for (IPermission permision : permisions) {
            if (!permisionList.contains(permision)) {
                permisionList.add(permision);
            }
        }
    }

    public void removePermissions(List<IPermission> permisions) {
        if (null == permisions || permisions.size() <= 0) {
            return;
        }
        for (IPermission permision : permisions) {
            if (permisionList.contains(permision)) {
                permisionList.remove(permision);
            }
        }
    }

    public IPermission[] getPermissionArray() {
        IPermission[] array = new IPermission[permisionList.size()];
        return permisionList.toArray(array);
    }

    public List<IPermission> getPermissionList() {
        return permisionList;
    }

    public Set<IPermission> getPermissionSet() {
        return new HashSet<IPermission>(permisionList);
    }

    public List<String> getPermissionString() {
        ArrayList<String> permisions = new ArrayList<String>(0);
        for (IPermission permision : permisionList) {
            permisions.add(permision.stringFormat());
        }
        return permisions;
    }
}
