package com.shawn.jooo.admin.rbac.dao;

import com.shawn.jooo.admin.rbac.entity.SysRoleMenu;
import com.shawn.jooo.framework.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 *
 * 角色和菜单关联表
 *
 * @author jooo.gen
 */
@Mapper
public interface SysRoleMenuMapper extends BaseMapper<SysRoleMenu,Integer> {

}