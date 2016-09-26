package com.infinite.eoa.service;

import com.infinite.eoa.entity.PersistentUserRole;

import java.util.List;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface PersistentUserRoleService {

    public List<PersistentUserRole> findByUserId(String appkey, String ids);

    public PersistentUserRole save(String appkey, PersistentUserRole userRole);

    public int deleteByUserId(String appkey, String userid);

    public int delete(String appkey, String userid, String roleid);

}
