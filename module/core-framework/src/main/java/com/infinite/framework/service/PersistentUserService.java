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

    /**
     * 新增/修改  用户信息
     * @param appkey
     * @param user
     * @return
     */
    public PersistentUser save(String appkey, PersistentUser user);

    public long delete(String appkey, String... ids);

}
