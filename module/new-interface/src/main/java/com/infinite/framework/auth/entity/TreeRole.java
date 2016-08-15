package com.infinite.framework.auth.entity;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 * Created by hx on 16-7-5.
 */
public class TreeRole extends BasicRole implements ITreeRole {
    protected ITreeRole parent;
    protected List<ITreeRole> childrenList = new ArrayList<ITreeRole>(0);

    public void addChild(ITreeRole child) {
        if (!childrenList.contains(child)) {
            childrenList.add(child);
        }
    }

    public void removeChild(ITreeRole child) {
        childrenList.remove(child);
    }

    public void addChildren(ITreeRole... children) {
        if (null == children || children.length <= 0) {
            return;
        }
        for (ITreeRole role : children) {
            if (!childrenList.contains(role)) {
                childrenList.add(role);
            }
        }
    }

    public void removeChildren(ITreeRole... children) {
        if (null == children || children.length <= 0) {
            return;
        }
        for (ITreeRole role : children) {
            if (!childrenList.contains(role)) {
                childrenList.remove(role);
            }
        }
    }

    public void addChildren(List<ITreeRole> children) {
        if (null == children || children.size() <= 0) {
            return;
        }
        for (ITreeRole role : children) {
            if (!childrenList.contains(role)) {
                childrenList.add(role);
            }
        }
    }

    public void removeChildren(List<ITreeRole> children) {
        if (null == children || children.size() <= 0) {
            return;
        }
        for (ITreeRole role : children) {
            if (!childrenList.contains(role)) {
                childrenList.remove(role);
            }
        }
    }

    public ITreeRole getParent() {
        return parent;
    }

    public void setParent(ITreeRole parent) {
        this.parent = parent;
    }

    public List<ITreeRole> getChildrenList() {
        return childrenList;
    }

    public void setChildrenList(List<ITreeRole> childrenList) {
        this.childrenList = childrenList;
    }

    public List<IPermission> getPermissionMergeAllChildren() {
        Stack<ITreeRole> childrenStack = new Stack<ITreeRole>();
        childrenStack.push(this);
        ArrayList<IPermission> permissions = new ArrayList<IPermission>();

        do {
            ITreeRole currentRole = childrenStack.pop();
            List<ITreeRole> children = currentRole.getChildrenList();
            if (null != children) {
                childrenStack.addAll(children);
            }
            permissions.addAll(currentRole.getPermissionList());
        } while (!childrenStack.isEmpty());
        return permissions;
    }

    public List<String> getPermissionStringMergeAllChildren() {
        Stack<ITreeRole> childrenStack = new Stack<ITreeRole>();
        childrenStack.push(this);
        ArrayList<String> permissions = new ArrayList<String>();

        do {
            ITreeRole currentRole = childrenStack.pop();
            List<ITreeRole> children = currentRole.getChildrenList();
            if (null != children) {
                childrenStack.addAll(children);
            }
            permissions.addAll(currentRole.getPermissionString());
        } while (!childrenStack.isEmpty());
        return permissions;
    }

    @Override
    public Document toDocument() {
        return super.toDocument();
    }
}
