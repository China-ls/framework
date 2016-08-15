package com.infinite.framework.service;

import com.infinite.framework.entity.Account;
import com.infinite.framework.entity.EntityConst;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface AccountService {
    public Account addAccount(String name, String username,
                              String password, EntityConst.AccountType type);

    public void updateAccountPassword(String id, String password);

    public void updateAccountName(String id, String name);
}
