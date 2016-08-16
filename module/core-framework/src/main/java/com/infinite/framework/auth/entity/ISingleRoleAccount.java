package com.infinite.framework.auth.entity;

import java.util.List;

/**
 * Created by hx on 16-7-5.
 */
public interface ISingleRoleAccount extends IAccount {

    ITreeRole getRole();

    void setRole(ITreeRole role);

    public List<IPermission> getPermisions();

    public List<String> getPermisionString();

}
