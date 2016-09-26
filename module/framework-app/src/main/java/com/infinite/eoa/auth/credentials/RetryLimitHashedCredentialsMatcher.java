package com.infinite.eoa.auth.credentials;

import com.infinite.eoa.core.util.TimeUtils;
import com.infinite.eoa.service.EmployeeAuthService;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.credential.SimpleCredentialsMatcher;

import java.util.concurrent.TimeUnit;

/**
 * @author by hx on 16-7-7.
 */
public class RetryLimitHashedCredentialsMatcher extends SimpleCredentialsMatcher {

    private EmployeeAuthService employeeAuthService;

    public EmployeeAuthService getEmployeeAuthService() {
        return employeeAuthService;
    }

    public void setEmployeeAuthService(EmployeeAuthService employeeAuthService) {
        this.employeeAuthService = employeeAuthService;
    }

    @Override
    public boolean doCredentialsMatch(AuthenticationToken token, AuthenticationInfo info) {
        String username = (String) token.getPrincipal();
        //retry count + 1
        int retryCount = employeeAuthService.increaseUserPasswordErrorCountAndGet(username, 1);
        if (retryCount > 2) {
            if (TimeUtils.isBeforeCurrentTime(employeeAuthService.getUserPasswordErrorCountExpireTime(username))) {
                employeeAuthService.resetUserPasswordErrorCount(username);
            }
        }
        if (retryCount > 5) {
            if (retryCount == 6) {
                employeeAuthService.setUserPasswordErrorCountExpireTime(username,
                        TimeUtils.getAfterCurrentTime(TimeUnit.MINUTES, 10));
            } else {
                //if retry count > 5 throw
                throw new ExcessiveAttemptsException();
            }

        }

        boolean matches = super.doCredentialsMatch(token, info);
        if (matches) {
            employeeAuthService.resetUserPasswordErrorCount(username);
            //clear retry count
        }
        return matches;
    }
}