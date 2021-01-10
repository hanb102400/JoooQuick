package com.shawn.admin.rbac.dao;

import org.apache.ibatis.annotations.Mapper;
import com.shawn.jooo.framework.base.BaseMapper;
import com.shawn.admin.rbac.entity.SysUser;


/**
 *
 * 用户信息表
 *
 * @author jooo.gen
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser,Long>{

}