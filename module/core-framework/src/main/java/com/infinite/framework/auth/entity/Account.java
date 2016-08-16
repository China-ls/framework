package com.infinite.framework.auth.entity;

import com.infinite.framework.core.entity.AbstractEntity;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by hx on 16-7-5.
 */
public class Account extends AbstractEntity implements ISingleRoleAccount {
    protected static Logger logger = LoggerFactory.getLogger(Account.class);
    protected ObjectId objectId;
    protected String name;
    protected String username;
    protected String password;
    protected int passwordRetryCount;
    protected ITreeRole role;

    public ObjectId getObjectId() {
        return objectId;
    }

    public void setObjectId(ObjectId objectId) {
        this.objectId = objectId;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Document toDocument() {
        Document document = new Document()
                .append("objectId", objectId)
                .append("username", username)
                .append("password", password);
        if (null != role) {
            document.append("role", role.toDocument());
            document.append("roleTypeName", role.getClass().getName());
        }
        return document;
    }

    public void fromDocument(Document document) {
        setObjectId(document.getObjectId("objectId"));
        setUsername(document.getString("username"));
        setPassword(document.getString("password"));
        Object roleObject = document.get("role");
        if (roleObject != null) {
            String roleTypeName = document.getString("roleTypeName");
            try {
                Class roleClass = Class.forName(roleTypeName);
                ITreeRole treeRole = (ITreeRole) roleClass.newInstance();
                treeRole.fromDocument((Document) roleObject);
                setRole(treeRole);
            } catch (ClassNotFoundException e) {
                logger.warn("try to find role type class error.{}", e);
            } catch (InstantiationException e) {
                logger.warn("try to instance role error.{}", e);
            } catch (IllegalAccessException e) {
                logger.warn("try to instance role access error.{}", e);
            }
        }
    }

    public ITreeRole getRole() {
        return role;
    }

    public void setRole(ITreeRole role) {
        this.role = role;
    }

    public List<String> getPermisionString() {
        return null == role ? null : role.getPermissionStringMergeAllChildren();
    }

    public List<IPermission> getPermisions() {
        if (null == role) {
            return null;
        }
        return role.getPermissionMergeAllChildren();
    }

    @Override
    public String toString() {
        return "AccountFN{" +
                "objectId=" + objectId +
                ", name='" + name + '\'' +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }
}
