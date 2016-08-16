package com.infinite.framework.auth.entity;

import com.infinite.framework.core.object.IDisable;
import com.infinite.framework.core.object.IDocumentable;
import com.infinite.framework.core.object.INameable;
import com.infinite.framework.core.object.IStringable;

/**
 * Created by hx on 16-7-5.
 */
public interface IPermission extends IStringable, IDocumentable, INameable, IDisable {

    String getDescribtion();

    void setDescribtion(String describtion);

}
