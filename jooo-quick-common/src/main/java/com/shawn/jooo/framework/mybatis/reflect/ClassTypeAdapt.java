package com.shawn.jooo.framework.mybatis.reflect;

import java.lang.reflect.ParameterizedType;

/**
 * Fn函数
 *
 * @author shawn
 */
public abstract class ClassTypeAdapt<T> {

    public Class<T> getClassType() {
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();
        Class<T> clazz = (Class<T>) parameterizedType.getActualTypeArguments()[0];
        return clazz;
    }
}
