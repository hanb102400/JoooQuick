package com.shawn.rbac.dao;

import org.apache.ibatis.annotations.Mapper;
import com.shawn.jooo.framework.base.BaseMapper;
import com.shawn.rbac.entity.SysUserRole;


/**
 *
 * 用户角色关联表
 *
 * @author jooo.gen
 */
@Mapper
public interface SysUserRoleMapper extends BaseMapper<SysUserRole,Integer>{

}