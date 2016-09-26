package com.infinite.eoa.service.impl;

import com.infinite.eoa.entity.Employee;
import com.infinite.eoa.service.EmployeeAuthService;
import com.infinite.eoa.service.EmployeeService;
import com.infinite.eoa.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("EmployeeAuthService")
public class EmployeeAuthServiceImpl implements EmployeeAuthService {
    @Autowired
    private EmployeeService employeeService;

    @Override
    public Employee getByUsername(String username) throws ServiceException {
        return employeeService.getByUsername(username);
    }

    @Override
    public boolean isUsernameExsist(String username) {
        return employeeService.isUsernameExsist(username);
    }

    @Override
    public void setUserPasswordErrorCountExpireTime(String username, long time) throws ServiceException {
    }

    @Override
    public long getUserPasswordErrorCountExpireTime(String username) throws ServiceException {
        return 0;
    }

    @Override
    public int getUserPasswordErrorCount(String username) throws ServiceException {
        return 0;
    }

    @Override
    public void increaseUserPasswordErrorCount(String username, int count) throws ServiceException {
    }

    @Override
    public int increaseUserPasswordErrorCountAndGet(String username, int count) throws ServiceException {
        return 0;
    }

    @Override
    public void resetUserPasswordErrorCount(String username) throws ServiceException {
    }
}
