package com.infinite.eoa.service.impl;

import com.infinite.eoa.entity.Application;
import com.infinite.eoa.persistent.ApplicationDAO;
import com.infinite.eoa.service.ApplicationService;
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
