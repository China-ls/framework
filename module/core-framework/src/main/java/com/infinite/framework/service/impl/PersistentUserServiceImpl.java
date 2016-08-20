package com.infinite.framework.service.impl;

import com.infinite.framework.core.util.JsonUtil;
import com.infinite.framework.entity.PersistentUser;
import com.infinite.framework.service.ApplicationService;
import com.infinite.framework.service.PersistentObjectService;
import com.infinite.framework.service.PersistentUserService;
import com.infinite.framework.service.exception.InvalidDataException;
import com.mongodb.client.model.Filters;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

/**
 * @author by hx on 16-8-9.
 * @since 1.0
 */
@Service("PersistentUserService")
public class PersistentUserServiceImpl implements PersistentUserService {

    private String userNameSpace = "__sys_persistent_user__";

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private PersistentObjectService persistentObjectService;

    @Override
    public PersistentUser findById(String appkey, String id) {
        applicationService.applicationExsist(appkey);
        Document document = persistentObjectService.get(appkey, userNameSpace, Filters.eq("_id", id));
        if (null == document) {
            return null;
        }
        return JsonUtil.fromJson(document.toJson(), PersistentUser.class);
    }

    @Override
    public List<PersistentUser> find(String appkey, String... ids) {
        applicationService.applicationExsist(appkey);
        return null;
    }

    @Override
    public PersistentUser saveOrUpdate(String appkey, PersistentUser user) {
        applicationService.applicationExsist(appkey);
        if (null == user) {
            throw new InvalidDataException("user is null");
        }
        if (StringUtils.isEmpty(user.getId())) {
            user.setId(UUID.randomUUID().toString());
        }
        if (StringUtils.isEmpty(user.getId())) {
            user.setId(UUID.randomUUID().toString());
        }
        persistentObjectService.put(appkey, userNameSpace, Document.parse(JsonUtil.toJson(user)));
        return user;
    }

    @Override
    public long delete(String appkey, String... ids) {
        applicationService.applicationExsist(appkey);
        return persistentObjectService.remove(appkey, userNameSpace, Filters.in("_id", ids));
    }
}
