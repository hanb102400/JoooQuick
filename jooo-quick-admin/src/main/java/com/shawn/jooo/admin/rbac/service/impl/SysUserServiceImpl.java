package com.shawn.jooo.admin.rbac.service.impl;

import com.shawn.jooo.admin.rbac.entity.SysUserRole;
import com.shawn.jooo.admin.rbac.service.SysUserRoleService;
import com.shawn.jooo.framework.mybatis.condition.Example;
import com.shawn.jooo.framework.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shawn.jooo.admin.rbac.entity.SysUser;
import com.shawn.jooo.admin.rbac.service.SysUserService;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 用户信息表
 *
 * @author jooo.gen
 */
@Service("sysUserService")
public class SysUserServiceImpl extends BaseServiceImpl<SysUser, Integer> implements SysUserService {

    @Autowired
    private SysUserRoleService sysUserRoleService;

    @Override
    public SysUser findByUsername(String username) {
        Example example = new Example();
        example.<SysUser>createCriteria().andEqualTo(SysUser::getUsername, username);
        return this.findOne(example).orElseGet(null);
    }

    @Override
    public SysUser findUserById(Integer userId) {
        SysUser sysUser = this.findById(userId).get();
        Example example = new Example();
        example.createCriteria().andEqualTo(SysUserRole::getUserId, sysUser.getUserId());
        Integer[] roleIds = sysUserRoleService.findAll(example).stream().map(item->item.getRoleId()).distinct().toArray(Integer[]::new);
        sysUser.setRoleIds(roleIds);
        return sysUser;
    }

    @Override
    @Transactional
    public void insertUser(SysUser sysUser) {
        this.insert(sysUser);
        this.insertUserRole(sysUser);
    }

    @Override
    @Transactional
    public void updateUser(SysUser sysUser) {
        this.deleteUserRole(sysUser);
        this.update(sysUser);
        this.insertUserRole(sysUser);
    }

    @Override
    @Transactional
    public void deleteUser(SysUser sysUser) {
        this.deleteUser(sysUser);
        this.deleteUserRole(sysUser);
    }

    private void deleteUserRole(SysUser sysUser) {
        Example example = new Example();
        example.createCriteria().andEqualTo(SysUserRole::getUserId, sysUser.getUserId());
        sysUserRoleService.delete(example);
    }

    private void insertUserRole(SysUser sysUser) {
        for (Integer roleId : sysUser.getRoleIds()) {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(sysUser.getUserId());
            sysUserRole.setRoleId(roleId);
            sysUserRoleService.insert(sysUserRole);
        }
    }
}



