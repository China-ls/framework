package com.infinite.eoa.auth.entity;

import com.infinite.eoa.core.object.IDisable;
import com.infinite.eoa.core.object.IDocumentable;
import com.infinite.eoa.core.object.INameable;
import com.infinite.eoa.core.object.IStringable;

/**
 * Created by hx on 16-7-5.
 */
public interface IPermission extends IStringable, IDocumentable, INameable, IDisable {

    String getDescribtion();

    void setDescribtion(String describtion);

}
