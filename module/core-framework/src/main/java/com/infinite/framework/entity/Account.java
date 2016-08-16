package com.infinite.framework.entity;

import com.infinite.framework.core.entity.AbstractEntity;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * AccountFN Entity Bean
 *
 * @author hx on 16-7-25.
 * @since 1.0
 */
@Entity(EntityConst.CollectionName.ACCOUNT)
public class Account extends AbstractEntity {
    @Id
    @Property
    private String id;
    @Property
    private String name;
    @Property
    private String username;
    @Property
    private String password;
    @Property
    private int passwordErrorCount;
    @Property
    private long passwordErrorCountExpireTime;
    @Property
    private String token;
    @Property
    private Timestamp tokenExpireTime;
    @Property
    private EntityConst.EntityStatus status = EntityConst.EntityStatus.NORMAL;
    @Property
    private EntityConst.AccountType type = EntityConst.AccountType.CLIENT;
    @Reference(lazy = true, ignoreMissing = true)
    private ArrayList<Application> applications = new ArrayList<Application>(0);
    @Property
    private HashMap<String, Object> fields = new HashMap<String, Object>(0);

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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPasswordErrorCount() {
        return passwordErrorCount;
    }

    public void setPasswordErrorCount(int passwordErrorCount) {
        this.passwordErrorCount = passwordErrorCount;
    }

    public long getPasswordErrorCountExpireTime() {
        return passwordErrorCountExpireTime;
    }

    public void setPasswordErrorCountExpireTime(long passwordErrorCountExpireTime) {
        this.passwordErrorCountExpireTime = passwordErrorCountExpireTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Timestamp getTokenExpireTime() {
        return tokenExpireTime;
    }

    public void setTokenExpireTime(Timestamp tokenExpireTime) {
        this.tokenExpireTime = tokenExpireTime;
    }

    public EntityConst.AccountType getType() {
        return type;
    }

    public void setType(EntityConst.AccountType type) {
        this.type = type;
    }

    public HashMap<String, Object> getFields() {
        return fields;
    }

    public void setFields(HashMap<String, Object> fields) {
        this.fields = fields;
    }

    public EntityConst.EntityStatus getStatus() {
        return status;
    }

    public void setStatus(EntityConst.EntityStatus status) {
        this.status = status;
    }

    public ArrayList<Application> getApplications() {
        return applications;
    }

    public void setApplications(ArrayList<Application> applications) {
        this.applications = applications;
    }

    public void addApplications(Application application) {
        this.applications.add(application);
    }

    public Account put(String key, Object value) {
        if (null == key || null == value) {
            return this;
        }
        if (!key.startsWith(EntityConst.FieldName.AccountFN.PREFIX)) {
            key = EntityConst.FieldName.AccountFN.PREFIX + key;
        }
        fields.put(key, value);
        return this;
    }

    public <T> Object get(String key) {
        if (null == key) {
            return null;
        }
        if (!key.startsWith(EntityConst.FieldName.AccountFN.PREFIX)) {
            key = EntityConst.FieldName.AccountFN.PREFIX + key;
        }
        return fields.get(key);
    }

    public Account put(Map<String, Object> values) {
        if (null == values) {
            return this;
        }
        for (Map.Entry<String, Object> entry : values.entrySet()) {
            put(entry.getKey(), entry.getValue());
        }
        return this;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", passwordErrorCount=" + passwordErrorCount +
                ", passwordErrorCountExpireTime=" + passwordErrorCountExpireTime +
                ", token='" + token + '\'' +
                ", tokenExpireTime=" + tokenExpireTime +
                ", status=" + status +
                ", type=" + type +
                ", applications=" + applications +
                ", fields=" + fields +
                '}';
    }
}
