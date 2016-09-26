package com.infinite.eoa.auth.entity;

import com.infinite.eoa.core.object.IDocumentable;
import com.infinite.eoa.core.object.INameable;
import com.infinite.eoa.core.object.IObjectIdable;

/**
 * Created by hx on 16-7-5.
 */
public interface IAccount extends INameable, IDocumentable, IObjectIdable {
    public String getUsername();

    public void setUsername(String username);

    public String getPassword();

    public void setPassword(String password);
}
