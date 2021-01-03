package com.shawn.jooo.framework.mybatis.condition;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class QueryVo<T> {

    private Class<T> classType;

    @SuppressWarnings("unchecked")
    public Class<T> getGenericClass() {
        Type superClass = getClass().getGenericSuperclass();
        Type  type = ((ParameterizedType) superClass).getActualTypeArguments()[0];
        if (type instanceof ParameterizedType) {
            this.classType = (Class<T>) ((ParameterizedType) type).getRawType();
        } else {
            this.classType = (Class<T>)type;
        }
        return  classType;
    }
}
