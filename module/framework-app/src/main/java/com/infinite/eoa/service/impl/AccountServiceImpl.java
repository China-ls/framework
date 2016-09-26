package com.infinite.eoa.service.impl;

import com.infinite.eoa.entity.Account;
import com.infinite.eoa.entity.EntityConst;
import com.infinite.eoa.persistent.AccountDAO;
import com.infinite.eoa.service.AccountService;
import com.infinite.eoa.service.exception.EntityExsistException;
import com.infinite.eoa.service.exception.EntityNotExsistException;
import org.apache.commons.lang.StringUtils;
import org.mongodb.morphia.query.UpdateResults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("AccountService")
public class AccountServiceImpl implements AccountService {
    @Autowired
    private AccountDAO accountDAO;

    @Override
    public Account addAccount(String name, String username, String password, EntityConst.AccountType type) {
        Account account = accountDAO.findByUsername(username);
        if (null != account) {
            throw new EntityExsistException("Account username : [" + username + "] is exsist.");
        }
        account = new Account();
        account.setUsername(username);
        account.setType(type);
        account.setPassword(password);
        accountDAO.save(account);
        return account;
    }

    @Override
    public void updateAccountPassword(String id, String password) {
        Account account = accountDAO.get(id);
        if (null == account) {
            throw new EntityNotExsistException("Account id : [" + id + "] is not exsist.");
        }
        if (StringUtils.equals(account.getPassword(), password)) {

        }
        UpdateResults results = accountDAO.updatePasswordById(id, password);
    }

    @Override
    public void updateAccountName(String id, String name) {
        UpdateResults results = accountDAO.updateName(id, name);
        if (results.getUpdatedCount() <= 0) {
            throw new EntityNotExsistException("Account id : [" + id + "] is not exsist.");
        }
    }
}
