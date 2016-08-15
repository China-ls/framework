package com.infinite.framework.module.flow;

import com.infinite.framework.module.IModule;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * Created by hx on 16-6-15.
 */
public abstract class AbstractSpringWorkFlow extends AbstractWorkFlow implements ISpringWorkFlow {
    @Override
    protected void attechModules() {}

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        moduleClass = getModules();
        if (null == moduleClass || moduleClass.length <= 0) {
            log.warn("You have defined a null modules WorkFlow :" + getClass().getName());
            return;
        }
        modules = new IModule[moduleClass.length];
        for (int i = 0; i < moduleClass.length; i++) {
            modules[i] = applicationContext.getBean(moduleClass[i]);
        }
        log.debug("WorkFlow inited:" + getClass().getName());
    }

}
