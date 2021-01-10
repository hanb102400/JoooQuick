package com.shawn.admin.rbac.dao;

import org.apache.ibatis.annotations.Mapper;
import com.shawn.jooo.framework.base.BaseMapper;
import com.shawn.admin.rbac.entity.SysMenu;


/**
 *
 * 菜单权限表
 *
 * @author jooo.gen
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu,Integer>{

}