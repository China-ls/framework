package com.infinite.framework.auth.entity;

import com.infinite.framework.core.object.IDocumentable;
import com.infinite.framework.core.object.INameable;
import com.infinite.framework.core.object.IObjectIdable;

/**
 * Created by hx on 16-7-5.
 */
public interface IAccount extends INameable, IDocumentable, IObjectIdable {
    public String getUsername();

    public void setUsername(String username);

    public String getPassword();

    public void setPassword(String password);
}
