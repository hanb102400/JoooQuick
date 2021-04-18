package com.shawn.jooo.admin.rbac.dao;

import com.shawn.jooo.admin.rbac.entity.SysOperateLog;
import com.shawn.jooo.framework.base.BaseMapper;
import org.apache.ibatis.annotations.Mapper;


/**
 *
 * 操作日志记录
 *
 * @author jooo.gen
 */
@Mapper
public interface SysOperateLogMapper extends BaseMapper<SysOperateLog,Integer> {

}