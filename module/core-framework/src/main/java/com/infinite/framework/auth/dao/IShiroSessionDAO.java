package com.infinite.framework.auth.dao;

import com.infinite.framework.core.persistent.IMongoDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;

/**
 * Created by hx on 16-7-5.
 */
public interface IShiroSessionDAO extends IMongoDAO, SessionDAO {
}
