package com.infinite.framework.persistent;

import com.infinite.framework.core.persistent.IMorphiaDAO;
import com.infinite.framework.entity.Account;
import org.mongodb.morphia.query.UpdateResults;

public interface AccountDAO extends IMorphiaDAO<Account, String> {

    public Account findById(String id);

    public Account findByUsername(String username);

    public boolean isUsernameExsist(String username);

    public int findUserPasswordErrorCount(String username);

    public long findUserPasswordErrorCountExpireTime(String username);

    public UpdateResults setUserPasswordErrorCountExpireTime(String username, long time);

    public UpdateResults resetUserPasswordErrorCount(String username);

    public UpdateResults increaseUserPasswordErrorCount(String username, int count);

    public UpdateResults updatePasswordByUsername(String username, String password);

    public UpdateResults updatePasswordById(String id, String password);

    public UpdateResults updateName(String id, String name);

}
