package com.infinite.eoa.core.web;

import com.infinite.eoa.core.web.entity.Response;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.Permission;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.ExecutionException;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by hx on 16-7-5.
 */
public class BasicController implements IController {
    public static String APPKEY = "aidiman/xgsn/yinfantech";

    private static Logger logger = LoggerFactory.getLogger(BasicController.class);

    public Response makeResponse(String code, String message) {
        return new Response(code, message);
    }

    public Response makeResponse(String code) {
        return new Response(code);
    }

    public Response makeResponse(String code, String message, Object data) {
        return new Response(code, message, data);
    }

    public Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    public SecurityManager getSecurityManager() {
        return SecurityUtils.getSecurityManager();
    }

    public Object getPrincipal() {
        return getSubject().getPrincipal();
    }

    public PrincipalCollection getPrincipals() {
        return getSubject().getPrincipals();
    }

    public boolean isPermitted(String permission) {
        return getSubject().isPermitted(permission);
    }

    public boolean isPermitted(Permission permission) {
        return getSubject().isPermitted(permission);
    }

    public boolean[] isPermitted(String... permissions) {
        return getSubject().isPermitted(permissions);
    }

    public boolean[] isPermitted(List<Permission> permissions) {
        return getSubject().isPermitted(permissions);
    }

    public boolean isPermittedAll(String... permissions) {
        return getSubject().isPermittedAll(permissions);
    }

    public boolean isPermittedAll(Collection<Permission> permissions) {
        return getSubject().isPermittedAll(permissions);
    }

    public void checkPermission(String permission) throws AuthorizationException {
        getSubject().checkPermission(permission);
    }

    public void checkPermission(Permission permission) throws AuthorizationException {
        getSubject().checkPermission(permission);
    }

    public void checkPermissions(String... permissions) throws AuthorizationException {
        getSubject().checkPermissions(permissions);
    }

    public void checkPermissions(Collection<Permission> permissions) throws AuthorizationException {
        getSubject().checkPermissions(permissions);
    }

    public boolean hasRole(String roleIdentifier) {
        return getSubject().hasRole(roleIdentifier);
    }

    public boolean[] hasRoles(List<String> roleIdentifiers) {
        return getSubject().hasRoles(roleIdentifiers);
    }

    public boolean hasAllRoles(Collection<String> roleIdentifiers) {
        return getSubject().hasAllRoles(roleIdentifiers);
    }

    public void checkRole(String roleIdentifier) throws AuthorizationException {
        getSubject().checkRole(roleIdentifier);
    }

    public void checkRoles(Collection<String> roleIdentifiers) throws AuthorizationException {
        getSubject().checkRoles(roleIdentifiers);
    }

    public void checkRoles(String... roleIdentifiers) throws AuthorizationException {
        getSubject().checkRoles(roleIdentifiers);
    }

    public void login(AuthenticationToken token) throws AuthenticationException {
        getSubject().login(token);
    }

    public boolean isAuthenticated() {
        return getSubject().isAuthenticated();
    }

    public boolean isRemembered() {
        return getSubject().isRemembered();
    }

    public Session getSession() {
        return getSubject().getSession();
    }

    public Session getSession(boolean create) {
        return getSubject().getSession(create);
    }

    public void logout() {
        getSubject().logout();
    }

    public <V> V execute(Callable<V> callable) throws ExecutionException {
        return getSubject().execute(callable);
    }

    public void execute(Runnable runnable) {
        getSubject().execute(runnable);
    }

    public <V> Callable<V> associateWith(Callable<V> callable) {
        return getSubject().associateWith(callable);
    }

    public Runnable associateWith(Runnable runnable) {
        return getSubject().associateWith(runnable);
    }

    public void runAs(PrincipalCollection principals) throws NullPointerException, IllegalStateException {
        getSubject().runAs(principals);
    }

    public boolean isRunAs() {
        return getSubject().isRunAs();
    }

    public PrincipalCollection getPreviousPrincipals() {
        return getSubject().getPreviousPrincipals();
    }

    public PrincipalCollection releaseRunAs() {
        return getSubject().releaseRunAs();
    }
}
