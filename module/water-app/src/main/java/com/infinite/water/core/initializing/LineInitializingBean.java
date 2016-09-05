package com.infinite.water.core.initializing;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * @author by hx on 16-8-22.
 */
public class LineInitializingBean implements InitializingBean {
    private static Logger log = LoggerFactory.getLogger(LineInitializingBean.class);

    private boolean throwWhenError = true;
    private List<Initializing> initializingBeens;

    public List<Initializing> getInitializingBeens() {
        return initializingBeens;
    }

    public void setInitializingBeens(List<Initializing> initializingBeens) {
        this.initializingBeens = initializingBeens;
    }

    public boolean isThrowWhenError() {
        return throwWhenError;
    }

    public void setThrowWhenError(boolean throwWhenError) {
        this.throwWhenError = throwWhenError;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        if (null != initializingBeens && initializingBeens.size() > 0) {
            for (Initializing bean : initializingBeens) {
                if (log.isDebugEnabled()) {
                    log.debug("{} is initializing.", bean.getClass());
                }
                try {
                    bean.initializing();
                } catch (Exception e) {
                    if (throwWhenError) {
                        throw e;
                    } else if (log.isErrorEnabled()) {
                        log.error("{} initializing error.", bean.getClass(), e);
                    }
                }
            }
        }
    }
}
