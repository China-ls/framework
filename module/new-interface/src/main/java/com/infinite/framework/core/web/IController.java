package com.infinite.framework.core.web;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;

/**
 * Created by hx handler 16-6-15.
 */
public interface IController extends Subject {

    Subject getSubject();

    SecurityManager getSecurityManager();

}
