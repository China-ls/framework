package com.infinite.framework.service.impl;

import com.infinite.framework.core.util.JsonUtil;
import com.infinite.framework.entity.PersistentUser;
import com.infinite.framework.persistent.obj.QueryFilters;
import com.infinite.framework.service.ApplicationService;
import com.infinite.framework.service.PersistentObjectService;
import com.infinite.framework.service.PersistentUserService;
import com.infinite.framework.service.exception.InvalidDataException;
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
        Document document = persistentObjectService.get(appkey, userNameSpace, QueryFilters.eq("_id", id).toJson());
        return JsonUtil.fromJson(document.toJson(), PersistentUser.class);
    }

    @Override
    public List<PersistentUser> find(String appkey, String... ids) {
        applicationService.applicationExsist(appkey);
        return null;
    }

    @Override
    public PersistentUser save(String appkey, PersistentUser user) {
        applicationService.applicationExsist(appkey);
        if (null == user) {
            throw new InvalidDataException("user is null");
        }
        user.setId(UUID.randomUUID().toString());
        if (StringUtils.isEmpty(user.getId())) {
            user.setId(UUID.randomUUID().toString());
        }
        persistentObjectService.put(appkey, userNameSpace, JsonUtil.toJson(user));
        return user;
    }

    @Override
    public int delete(String appkey, String... ids) {
        applicationService.applicationExsist(appkey);
        return persistentObjectService.remove(appkey, userNameSpace, QueryFilters.in("_id", ids).toJson());
    }
}
