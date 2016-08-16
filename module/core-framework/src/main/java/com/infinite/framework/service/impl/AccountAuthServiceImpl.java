package com.infinite.framework.service.impl;

import com.infinite.framework.entity.Account;
import com.infinite.framework.persistent.AccountDAO;
import com.infinite.framework.service.AccountAuthService;
import com.infinite.framework.service.exception.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AccountAuthService")
public class AccountAuthServiceImpl implements AccountAuthService {
    @Autowired
    private AccountDAO accountDAO;

    @Override
    public Account getByUsername(String username) throws ServiceException {
        return accountDAO.findByUsername(username);
    }

    @Override
    public boolean isUsernameExsist(String username) {
        return accountDAO.isUsernameExsist(username);
    }

    @Override
    public void setUserPasswordErrorCountExpireTime(String username, long time) throws ServiceException {
        accountDAO.setUserPasswordErrorCountExpireTime(username, time);
    }

    @Override
    public long getUserPasswordErrorCountExpireTime(String username) throws ServiceException {
        return accountDAO.findUserPasswordErrorCountExpireTime(username);
    }

    @Override
    public int getUserPasswordErrorCount(String username) throws ServiceException {
        return accountDAO.findUserPasswordErrorCount(username);
    }

    @Override
    public void increaseUserPasswordErrorCount(String username, int count) throws ServiceException {
//        UpdateResults results =
        accountDAO.increaseUserPasswordErrorCount(username, count);
    }

    @Override
    public int increaseUserPasswordErrorCountAndGet(String username, int count) throws ServiceException {
        accountDAO.increaseUserPasswordErrorCount(username, count);
        return accountDAO.findUserPasswordErrorCount(username);
    }

    @Override
    public void resetUserPasswordErrorCount(String username) throws ServiceException {
        accountDAO.resetUserPasswordErrorCount(username);
    }
}
