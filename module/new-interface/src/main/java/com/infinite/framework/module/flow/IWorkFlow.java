package com.infinite.framework.module.flow;

import com.infinite.framework.module.IModule;

/**
 * Created by hx handler 16-6-15.
 */
public interface IWorkFlow extends IModule {

    /**
     * 流水线工作策略
     */
    public enum Tactics {
        /**
         * 忽略错误，一直执行到最后
         */
        IGNORE_EXCEPTION,
        /**
         * 全部成功，否则停止
         */
        ALL_SUCCESS
    }

    /**
     * 获取当前流水线工作策略
     *
     * @return 策略
     */
    public Tactics getTactics();

    /**
     * 获取 流水线上所有到模块
     *
     * @return 所有配置的模块
     */
    public Class<? extends IModule>[] getModules();

    public void setModules(Class<? extends IModule>[] modules);

}
