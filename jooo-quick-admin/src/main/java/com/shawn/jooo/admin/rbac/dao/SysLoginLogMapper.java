package com.shawn.jooo.admin.rbac.dao;

import com.shawn.jooo.admin.rbac.entity.SysLoginLog;
import com.shawn.jooo.framework.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 *
 * 系统访问记录
 *
 * @author jooo.gen
 */
@Mapper
public interface SysLoginLogMapper extends BaseMapper<SysLoginLog,Integer> {

}