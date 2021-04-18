package com.shawn.jooo.admin.rbac.dao;

import com.shawn.jooo.admin.rbac.entity.SysUser;
import com.shawn.jooo.framework.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 *
 * 用户信息表
 *
 * @author jooo.gen
 */
@Mapper
public interface SysUserMapper extends BaseMapper<SysUser,Long> {

}