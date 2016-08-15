package com.infinite.framework.service;

import com.infinite.framework.entity.Account;
import com.infinite.framework.service.exception.ServiceException;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface AccountAuthService {

    public Account getByUsername(String username) throws ServiceException;

    public boolean isUsernameExsist(String username) throws ServiceException;

    public void setUserPasswordErrorCountExpireTime(String username, long time) throws ServiceException;

    public long getUserPasswordErrorCountExpireTime(String username) throws ServiceException;

    public int getUserPasswordErrorCount(String username) throws ServiceException;

    public void increaseUserPasswordErrorCount(String username, int count) throws ServiceException;

    public int increaseUserPasswordErrorCountAndGet(String username, int count) throws ServiceException;

    public void resetUserPasswordErrorCount(String username) throws ServiceException;


}
