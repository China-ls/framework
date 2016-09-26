package com.infinite.eoa.service;

import com.infinite.eoa.entity.Employee;
import com.infinite.eoa.service.exception.ServiceException;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface EmployeeAuthService {

    public Employee getByUsername(String username) throws ServiceException;

    public boolean isUsernameExsist(String username) throws ServiceException;

    public void setUserPasswordErrorCountExpireTime(String username, long time) throws ServiceException;

    public long getUserPasswordErrorCountExpireTime(String username) throws ServiceException;

    public int getUserPasswordErrorCount(String username) throws ServiceException;

    public void increaseUserPasswordErrorCount(String username, int count) throws ServiceException;

    public int increaseUserPasswordErrorCountAndGet(String username, int count) throws ServiceException;

    public void resetUserPasswordErrorCount(String username) throws ServiceException;


}
