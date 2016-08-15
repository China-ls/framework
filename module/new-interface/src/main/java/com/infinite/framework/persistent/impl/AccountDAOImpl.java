package com.infinite.framework.persistent.impl;

import com.infinite.framework.core.persistent.MorphiaDAO;
import com.infinite.framework.entity.Account;
import com.infinite.framework.persistent.AccountDAO;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.UpdateResults;

/**
 * Created by hx on 16-7-27.
 */
public class AccountDAOImpl extends MorphiaDAO<Account, String> implements AccountDAO {
    public AccountDAOImpl(Datastore ds) {
        super(ds);
    }

    @Override
    public Account findById(String id) {
        return findOne("id", id);
    }

    @Override
    public Account findByUsername(String username) {
        return findOne("username", username);
    }

    @Override
    public boolean isUsernameExsist(String username) {
        return exists("username", username);
    }

    @Override
    public int findUserPasswordErrorCount(String username) {
        Account account = findOne(
                createQuery().filter("username", username)
                        .retrievedFields(true, "passwordErrorCount"));
        return account.getPasswordErrorCount();
    }

    @Override
    public long findUserPasswordErrorCountExpireTime(String username) {
        Account account = findOne(
                createQuery().filter("username", username)
                        .retrievedFields(true, "passwordErrorCountExpireTime"));
        return account.getPasswordErrorCountExpireTime();
    }

    @Override
    public UpdateResults setUserPasswordErrorCountExpireTime(String username, long time) {
        return update(
                createQuery().filter("username", username),
                createUpdateOperations().set("passwordErrorCountExpireTime", time)
        );
    }

    @Override
    public UpdateResults resetUserPasswordErrorCount(String username) {
        return update(
                createQuery().filter("username", username),
                createUpdateOperations().set("passwordErrorCount", 0)
        );
    }

    @Override
    public UpdateResults increaseUserPasswordErrorCount(String username, int count) {
        return update(
                createQuery().filter("username", username),
                createUpdateOperations().inc("passwordErrorCount", count)
        );
    }

    @Override
    public UpdateResults updatePasswordByUsername(String username, String password) {
        return update(
                createQuery().filter("username", username),
                createUpdateOperations().set("password", password)
        );
    }

    @Override
    public UpdateResults updatePasswordById(String id, String password) {
        return update(
                createQuery().filter("id", id),
                createUpdateOperations().set("password", password)
        );
    }

    @Override
    public UpdateResults updateName(String id, String name) {
        return update(
                createQuery().filter("id", id),
                createUpdateOperations().set("name", name)
        );
    }
}
