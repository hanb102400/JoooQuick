package com.shawn.jooo.framework.base;




import com.shawn.jooo.framework.mybatis.mapper.MapperAdapt;

import java.io.Serializable;

/**
 * 基础的mybatis的mapper类
 * 方法参考mybatis generator的接口实现
 *
 * @author shawn
 */
public interface BaseMapper<T, ID extends Serializable> extends MapperAdapt<T, ID> {

}
