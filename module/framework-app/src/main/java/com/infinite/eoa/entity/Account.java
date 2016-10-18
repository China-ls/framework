package com.infinite.eoa.entity;

import com.infinite.eoa.core.entity.AbstractEntity;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Property;
import org.mongodb.morphia.annotations.Reference;

import java.sql.Timestamp;
import java.util.ArrayList;

/**
 * AccountFN Entity Bean
 *
 * @author hx on 16-7-25.
 * @since 1.0
 */
@Entity(value = EntityConst.CollectionName.ACCOUNT, noClassnameStored = true)
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

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
