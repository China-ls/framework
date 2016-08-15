package com.infinite.framework.service;

import com.infinite.framework.entity.Application;
import com.infinite.framework.service.exception.ApplcationNotExsistException;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface ApplicationService {

    public boolean applicationExsist(String appkey) throws ApplcationNotExsistException;

    public Application createApplication(String name);

    public Application updateApplication(Application application);

}
