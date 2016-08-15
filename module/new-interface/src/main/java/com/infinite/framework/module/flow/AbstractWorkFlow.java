package com.infinite.framework.module.flow;

import com.infinite.framework.core.entity.AbstractEntity;
import com.infinite.framework.module.AbstractModule;
import com.infinite.framework.module.IModule;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;

/**
 * Created by hx on 16-6-15.
 */
public abstract class AbstractWorkFlow extends AbstractModule implements IWorkFlow {
    protected ApplicationContext context;
    protected Class<? extends IModule>[] moduleClass;
    protected IModule[] modules;

    public AbstractWorkFlow() {
        attechModules();
    }

    protected void attechModules() {
        moduleClass = getModules();
        if (null == moduleClass || moduleClass.length <= 0) {
            log.warn("You have defined a null modules WorkFlow :" + getClass().getName());
            return;
        }
        modules = new IModule[moduleClass.length];
        for (int i = 0; i < moduleClass.length; i++) {
            try {
                modules[i] = moduleClass[i].newInstance();
            } catch (InstantiationException e) {
                log.error("error to instantiation module:" + getClass().getName());
            } catch (IllegalAccessException e) {
                log.error("the module should have a public default construct:" + getClass().getName());
            }
        }
        log.debug("WorkFlow inited:" + getClass().getName());
    }

    public AbstractEntity handler(AbstractEntity orienData, AbstractEntity flowData) {
        log.debug("WorkFlow started:" + getClass().getName());
        for (IModule module : modules) {
            try {
                if (module.support(orienData, flowData)) {
                    flowData = module.handler(orienData, flowData);
                }
            } catch (Exception e) {
                log.warn("workflow hadler exception:" + e);
                if (Tactics.ALL_SUCCESS == getTactics()) {
                    break;
                }
            }
        }
        log.debug("WorkFlow finished:" + getClass().getName());
        return flowData;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {

    }

    public Tactics getTactics() {
        return Tactics.ALL_SUCCESS;
    }

    public void setModules(Class<? extends IModule>[] modules) {
        this.moduleClass = modules;
    }
}
