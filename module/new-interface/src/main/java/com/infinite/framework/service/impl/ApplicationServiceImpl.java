package com.infinite.framework.service.impl;

import com.infinite.framework.entity.Application;
import com.infinite.framework.persistent.ApplicationDAO;
import com.infinite.framework.service.ApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("ApplicationService")
public class ApplicationServiceImpl implements ApplicationService {
    @Autowired
    private ApplicationDAO applicationDAO;

    @Override
    public boolean applicationExsist(String appkey) {
        return true;
    }

    @Override
    public Application createApplication(String name) {
        return null;
    }

    @Override
    public Application updateApplication(Application application) {
        return null;
    }
}
