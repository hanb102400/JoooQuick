package com.shawn.admin.rbac.dao;

import org.apache.ibatis.annotations.Mapper;
import com.shawn.jooo.framework.base.BaseMapper;
import com.shawn.admin.rbac.entity.SysRoleMenu;


/**
 *
 * 角色和菜单关联表
 *
 * @author jooo.gen
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu,Integer>{

}