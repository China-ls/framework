package com.infinite.framework.module;

import com.infinite.framework.core.entity.AbstractEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hx on 16-6-15.
 */
public abstract class AbstractModule implements IModule {

    protected Logger log = LoggerFactory.getLogger(getClass());

    public boolean support(AbstractEntity orienData, AbstractEntity flowData) {
        return true;
    }
}
