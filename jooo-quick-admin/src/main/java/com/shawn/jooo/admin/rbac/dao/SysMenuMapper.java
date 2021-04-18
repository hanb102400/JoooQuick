package com.shawn.jooo.admin.rbac.dao;

import com.shawn.jooo.admin.rbac.entity.SysMenu;
import com.shawn.jooo.framework.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 *
 * 菜单权限表
 *
 * @author jooo.gen
 */
@Mapper
public interface SysMenuMapper extends BaseMapper<SysMenu,Integer> {

}