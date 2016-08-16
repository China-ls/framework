package com.infinite.framework.auth.entity;

import com.infinite.framework.core.object.IDisable;
import com.infinite.framework.core.object.IDocumentable;
import com.infinite.framework.core.object.INameable;
import com.infinite.framework.core.object.IStringable;

import java.util.List;
import java.util.Set;

/**
 * Created by hx on 16-7-5.
 */
public interface IRole extends INameable, IDisable, IDocumentable, IStringable {

    void addPermission(IPermission permision);

    void removePermission(IPermission permision);

    void addPermissions(IPermission... permisions);

    void removePermissions(IPermission... permisions);

    void addPermissions(List<IPermission> permisions);

    void removePermissions(List<IPermission> permisions);

    IPermission[] getPermissionArray();

    List<IPermission> getPermissionList();

    Set<IPermission> getPermissionSet();

    List<String> getPermissionString();

}
