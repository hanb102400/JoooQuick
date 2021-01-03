package com.shawn.rbac.dao;

import org.apache.ibatis.annotations.Mapper;
import com.shawn.jooo.framework.base.BaseMapper;
import com.shawn.rbac.entity.SysRole;


/**
 *
 * 角色信息表
 *
 * @author jooo.gen
 */
@Mapper
public interface SysRoleMapper extends BaseMapper<SysRole,Long>{

}