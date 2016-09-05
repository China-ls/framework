package com.infinite.framework.service.impl;

import com.infinite.framework.core.util.JsonUtil;
import com.infinite.framework.entity.PersistentUser;
import com.infinite.framework.service.ApplicationService;
import com.infinite.framework.service.PersistentObjectService;
import com.infinite.framework.service.PersistentUserService;
import com.infinite.framework.service.exception.InvalidDataException;
import com.mongodb.client.model.Filters;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
        List<Document> documents = persistentObjectService.get(
                appkey, userNameSpace, Filters.eq("_id", new ObjectId(id)),
                0, 1, null, null, null, null
        );
        if (null == documents || documents.size() <= 0) {
            return null;
        }
        return JsonUtil.fromJson(documents.get(0).toJson(), PersistentUser.class);
    }

    @Override
    public List<PersistentUser> find(String appkey, String... ids) {
        applicationService.applicationExsist(appkey);
        if (null == ids || ids.length <= 0) {
            return null;
        }
        ArrayList<ObjectId> objectIds = new ArrayList<ObjectId>();
        for (String id : ids) {
            objectIds.add(new ObjectId(id));
        }
        List<Document> documents = persistentObjectService.get(appkey, userNameSpace, Filters.in("_id", objectIds),
                0, 0, null, null, null, null);
        return null;
    }

    @Override
    public PersistentUser save(String appkey, PersistentUser user) {
        applicationService.applicationExsist(appkey);
        if (null == user) {
            throw new InvalidDataException("user is null");
        }
        ObjectId id = persistentObjectService.put(appkey, userNameSpace, Document.parse(JsonUtil.toJson(user)));
        user.setId(id.toHexString());
        return user;
    }

    @Override
    public long delete(String appkey, String... ids) {
        applicationService.applicationExsist(appkey);
        if (null == ids || ids.length <= 0) {
            return 0;
        }
        ArrayList<ObjectId> objectIds = new ArrayList<ObjectId>();
        for (String id : ids) {
            objectIds.add(new ObjectId(id));
        }
        return persistentObjectService.remove(appkey, userNameSpace, Filters.in("_id", objectIds));
    }
}
