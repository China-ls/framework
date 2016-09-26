package com.infinite.eoa.auth.dao;

import com.infinite.eoa.core.persistent.MongoDAO;
import com.infinite.eoa.core.util.JsonUtil;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.UnknownSessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.util.Collection;

/**
 * Created by hx on 16-7-5.
 */
public class ShiroMongoDAO extends MongoDAO implements IShiroSessionDAO {
    private static Logger logger = LoggerFactory.getLogger(ShiroMongoDAO.class);

    public Serializable create(Session session) {
        logger.debug(JsonUtil.toJson(session));
        return null;
    }

    public Session readSession(Serializable sessionId) throws UnknownSessionException {
        return null;
    }

    public void update(Session session) throws UnknownSessionException {

    }

    public void delete(Session session) {

    }

    public Collection<Session> getActiveSessions() {
        return null;
    }
}
