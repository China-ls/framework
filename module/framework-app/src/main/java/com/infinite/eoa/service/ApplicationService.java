package com.infinite.eoa.service;

import com.infinite.eoa.entity.Application;
import com.infinite.eoa.service.exception.ApplicationNotExsistException;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface ApplicationService {

    public boolean applicationExsist(String appkey) throws ApplicationNotExsistException;

    public Application createApplication(String name);

    public Application updateApplication(Application application);

}
