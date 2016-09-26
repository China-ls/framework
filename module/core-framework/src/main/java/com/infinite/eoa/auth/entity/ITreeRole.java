package com.infinite.eoa.auth.entity;

import com.infinite.eoa.core.object.IDocumentable;
import com.infinite.eoa.core.object.INameable;
import com.infinite.eoa.core.object.IStringable;

import java.util.List;

/**
 * Created by hx on 16-7-5.
 */
public interface ITreeRole extends IRole, INameable, IDocumentable, IStringable {

    void addChild(ITreeRole child);

    void removeChild(ITreeRole child);

    void addChildren(ITreeRole... children);

    void removeChildren(ITreeRole... children);

    void addChildren(List<ITreeRole> children);

    void removeChildren(List<ITreeRole> children);

    IRole getParent();

    void setParent(ITreeRole parent);

    List<ITreeRole> getChildrenList();

    void setChildrenList(List<ITreeRole> childrenList);

    public List<IPermission> getPermissionMergeAllChildren();

    public List<String> getPermissionStringMergeAllChildren();

}
