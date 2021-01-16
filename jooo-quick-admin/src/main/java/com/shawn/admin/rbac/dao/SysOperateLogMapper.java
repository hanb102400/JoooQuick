package com.shawn.admin.rbac.dao;

import org.apache.ibatis.annotations.Mapper;
import com.shawn.jooo.framework.base.BaseMapper;
import com.shawn.admin.rbac.entity.SysOperateLog;


/**
 *
 * 操作日志记录
 *
 * @author jooo.gen
 */
@Mapper
public interface SysOperateLogMapper extends BaseMapper<SysOperateLog,Integer>{

}