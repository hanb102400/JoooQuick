package com.shawn.rbac.dao;

import org.apache.ibatis.annotations.Mapper;
import com.shawn.jooo.framework.base.BaseMapper;
import com.shawn.rbac.entity.SysLoginLog;


/**
 *
 * 系统访问记录
 *
 * @author jooo.gen
 */
@Mapper
public interface SysLoginLogMapper extends BaseMapper<SysLoginLog,Integer>{

}