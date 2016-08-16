package com.infinite.framework.auth.credentials;

import com.infinite.framework.core.util.TimeUtils;
import com.infinite.framework.service.AccountAuthService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import java.util.concurrent.TimeUnit;

/**
 * @author by hx on 16-7-7.
 */
public class RetryLimitHashedCredentialsMatcher extends SimpleCredentialsMatcher {

    private AccountAuthService accountAuthService;

    public AccountAuthService getAccountAuthService() {
        return accountAuthService;
    }

    public void setAccountAuthService(AccountAuthService accountAuthService) {
        this.accountAuthService = accountAuthService;
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String) token.getPrincipal();
        //retry count + 1
        int retryCount = accountAuthService.increaseUserPasswordErrorCountAndGet(username, 1);
        if (retryCount > 2) {
            if (TimeUtils.isBeforeCurrentTime(accountAuthService.getUserPasswordErrorCountExpireTime(username))) {
                accountAuthService.resetUserPasswordErrorCount(username);
            }
        }
        if (retryCount > 5) {
            if (retryCount == 6) {
                accountAuthService.setUserPasswordErrorCountExpireTime(username,
                        TimeUtils.getAfterCurrentTime(TimeUnit.MINUTES, 10));
            } else {
                //if retry count > 5 throw
                throw new ExcessiveAttemptsException();
            }

        }

        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            accountAuthService.resetUserPasswordErrorCount(username);
            //clear retry count
        }
        return matches;
    }
}