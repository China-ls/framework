package com.infinite.framework.service;

import com.infinite.framework.entity.PersistentUser;

import java.util.List;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface PersistentUserService {

    public PersistentUser findById(String appkey, String id);

    public List<PersistentUser> find(String appkey, String... ids);

    public PersistentUser save(String appkey, PersistentUser user);

    public int delete(String appkey, String... ids);

}
