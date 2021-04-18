package com.shawn.jooo.admin.rbac.dao;

import com.shawn.jooo.admin.rbac.entity.SysUserRole;
import com.shawn.jooo.framework.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 *
 * 用户角色关联表
 *
 * @author jooo.gen
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole,Integer> {

}