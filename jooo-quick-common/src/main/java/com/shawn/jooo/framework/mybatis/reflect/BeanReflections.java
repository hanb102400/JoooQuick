package com.shawn.jooo.framework.mybatis.reflect;


import org.apache.ibatis.type.JdbcType;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.*;

/**
 * BeanReflections
 *
 * @author shawn
 */
public class BeanReflections {

    public static Class<?> getEntityType(Class<?> mapperType) {
        Type[] types = mapperType.getGenericInterfaces();
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                ParameterizedType paramType = ParameterizedType.class.cast(type);
                Type actType = paramType.getActualTypeArguments()[0];
                if (actType instanceof Class) {
                    return Class.class.cast(actType);
                }
            }
        }
        throw new IllegalStateException("cant find  GenericInterfaces of " + mapperType.getName() + "");
    }


    public static String getTableName(Class<?> entityType) {
        Table table = entityType.getAnnotation(Table.class);
        if (table != null && table.name() != null && !"".equals(table.name())) {
            return table.name();
        } else {
            return JdbcTypes.getImprovedName(entityType.getSimpleName());
        }
    }


    public static boolean isId(Field field) {
        if (field.isAnnotationPresent(Transient.class)) {
            return false;
        }
        if (field.isAnnotationPresent(Id.class)) {
            return true;
        }
        return false;
    }

    public static boolean isColumn(Field field) {
        if (field.isAnnotationPresent(Transient.class)) {
            return false;
        }
        if (field.isAnnotationPresent(Column.class) || field.isAnnotationPresent(Id.class)) {
            return true;
        }
        return false;
    }

    public static String getColumnName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            String columnName = field.getAnnotation(Column.class).name();
            if (columnName != null && !"".equals(columnName)) {
                return columnName;
            }
            return JdbcTypes.getImprovedName(field.getName());
        }
        if (field.isAnnotationPresent(Id.class)) {
            String columnName = field.getName();
            columnName = JdbcTypes.getImprovedName(columnName);
            return columnName;
        }
        return null;
    }

    public static boolean isSerialVersionUID(Field field){
        return "serialVersionUID".equals(field.getName());
    }


    public static String getColumnTypeName(Field field) {
        Class<?> type = field.getType();
        return JdbcTypes.calculateJdbcTypeName(type);
    }

    public static JdbcType getJdbcType(Field field) {
        Class<?> type = field.getType();
        return JdbcTypes.calculateJdbcType(type);
    }

    public static String getFiledName(Field field) {
        return field.getName();
    }

    public static String getFieldTypeName(Field field) {
        Class<?> type = field.getType();
        return type.getName();
    }

    public static Object invokeGetMethod(Field field, Object obj) {
        Object value;
        try {
            PropertyDescriptor pd = new PropertyDescriptor(field.getName(), obj.getClass());
            Method getMethod = pd.getReadMethod();
            value = getMethod.invoke(obj);
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("invokeGetMethod error:" + obj.getClass().getName() + ":" + field.getName());

        }
        return value;
    }

    public static Class<?> getFieldType(Field field) {
        return field.getType();
    }
}
