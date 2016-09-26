package com.infinite.eoa.service;

import com.infinite.eoa.entity.PersistentRole;

import java.util.List;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface PersistentRoleService {

    public PersistentRole findOne(String appkey, String id);

    public List<PersistentRole> find(String appkey, String... ids);

    public PersistentRole save(String appkey, PersistentRole user);

    public int delete(String appkey, String... ids);

}
