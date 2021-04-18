package com.shawn.jooo.admin.rbac.service;

import com.shawn.jooo.framework.base.BaseService;
import com.shawn.jooo.admin.rbac.entity.SysUser;

/**
 *
 * 用户信息表
 *
 * @author jooo.gen
 */
public interface SysUserService extends BaseService<SysUser,Integer> {

    SysUser findByUsername(String username);

    SysUser findUserById(Integer userId);

    void insertUser(SysUser sysUser);

    void updateUser(SysUser sysUser);

    void deleteUser(SysUser sysUser);
}

