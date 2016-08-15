package com.infinite.framework.module;

import com.infinite.framework.core.entity.AbstractEntity;

/**
 * Created by hx handler 16-6-15.
 */
public interface IModule {

    public boolean support(AbstractEntity orienData, AbstractEntity flowData);

    public AbstractEntity handler(AbstractEntity orienData, AbstractEntity flowData);

}
