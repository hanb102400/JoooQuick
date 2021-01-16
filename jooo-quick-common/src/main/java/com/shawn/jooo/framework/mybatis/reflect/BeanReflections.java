package com.shawn.jooo.framework.mybatis.reflect;


import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.lang.annotation.Annotation;
import java.lang.reflect.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * BeanReflections
 *
 * @author shawn
 */
public class BeanReflections {


    /**
     * 获取泛型类的第1个参数类
     *
     * @param mapperType
     * @return
     */
    public static Class<?> getEntityType(Class<?> mapperType) {
        return getEntityType(mapperType, 0);
    }

    /**
     * 获取泛型类的第N个参数类
     *
     * @param mapperType
     * @return
     */
    public static Class<?> getEntityType(Class<?> mapperType, int N) {
        Type[] types = mapperType.getGenericInterfaces();
        for (Type type : types) {
            if (type instanceof ParameterizedType) {
                ParameterizedType paramType = ParameterizedType.class.cast(type);
                Type actType = paramType.getActualTypeArguments()[N];
                if (actType instanceof Class) {
                    return Class.class.cast(actType);
                }
            }
        }
        throw new IllegalStateException("cant find  GenericInterfaces of " + mapperType.getName() + "");
    }


    /**
     * 判断类是否为表映射类
     *
     * @param entityType
     * @return
     */
    public static boolean isTable(Class<?> entityType) {
        return entityType.isAnnotationPresent(Table.class);
    }

    /**
     * 判断属性是否为id字段
     *
     * @param field
     * @return
     */
    public static boolean isId(Field field) {
        if (field.isAnnotationPresent(Transient.class)) {
            return false;
        }
        return field.isAnnotationPresent(Id.class);
    }

    /**
     * 判断属性是否为column字段（包含id字段）
     *
     * @param field
     * @return
     */
    public static boolean isColumn(Field field) {
        if (field.isAnnotationPresent(Transient.class)) {
            return false;
        }
        return field.isAnnotationPresent(Column.class)
                || field.isAnnotationPresent(Id.class);
    }

    /**
     * 判断是否序列化特殊字段
     *
     * @param field
     * @return
     */
    public static boolean isSerialVersionUID(Field field) {
        return "serialVersionUID".equals(field.getName());
    }


    /**
     * 获取表名
     *
     * @param entityType
     * @return
     */
    public static String getTableName(Class<?> entityType) {
        Table table = entityType.getAnnotation(Table.class);
        if (table != null && StringUtils.isNotEmpty(table.name())) {
            return table.name();
        } else {
            return JdbcTypes.getUnderscoreName(entityType.getSimpleName());
        }
    }

    /**
     * 获取列名
     *
     * @param field
     * @return
     */
    public static String getColumnName(Field field) {
        if (field.isAnnotationPresent(Column.class)) {
            String columnName = field.getAnnotation(Column.class).name();
            if (StringUtils.isNotEmpty(columnName)) {
                return columnName;
            }
            return JdbcTypes.getUnderscoreName(field.getName());
        }
        if (field.isAnnotationPresent(Id.class)) {
            String columnName = field.getName();
            columnName = JdbcTypes.getUnderscoreName(columnName);
            return columnName;
        }
        return null;
    }

    /**
     * 获取列类型名称
     *
     * @param field
     * @return
     */
    public static String getColumnTypeName(Field field) {
        Class<?> type = field.getType();
        return JdbcTypes.calculateJdbcTypeName(type);
    }

    /**
     * 获取列jdbc类型
     *
     * @param field
     * @return
     */
    public static JdbcType getColumnJdbcType(Field field) {
        Class<?> type = field.getType();
        return JdbcTypes.calculateJdbcType(type);
    }

    /**
     * 获取属性名称
     *
     * @param field
     * @return
     */
    public static String getFieldName(Field field) {
        return field.getName();
    }

    /**
     * 获取属性类型
     *
     * @param field
     * @return
     */
    public static Class<?> getFieldType(Field field) {
        return field.getType();
    }

    /**
     * 获取属性类型名称
     *
     * @param field
     * @return
     */
    public static String getFieldTypeName(Field field) {
        Class<?> type = field.getType();
        return type.getName();
    }

    /**
     * 获取所有属性
     *
     * @param entityType
     * @return
     */
    public static List<Field> getFields(Class<?> entityType) {
        return FieldUtils.getAllFieldsList(entityType);
    }

    /**
     * 根据注解获取属性
     *
     * @param entityType
     * @param annotationCls
     * @return
     */
    public static List<Field> getFieldsWithAnnotation(Class<?> entityType, Class<? extends Annotation> annotationCls) {
        return FieldUtils.getFieldsListWithAnnotation(entityType, annotationCls);
    }

    public static Object readField(String name, Object entity) {
        Object val;
        try {
            Field field = FieldUtils.getField(entity.getClass(), name,true);
            val = FieldUtils.readField(field, entity,true);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("read field error:" + entity.getClass().getName() + ":" + name);
        }
        return val;
    }

    public static Object readField(Field field, Object target) {
        Object val;
        try {
            val = FieldUtils.readField(field, target,true);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("read field error:" + target.getClass().getName() + ":" + field.getName());
        }
        return val;
    }

    public static void writeField(Field field, Object value, Object target) {
        try {
            FieldUtils.writeField(field, target, value,true);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("write field error:" + target.getClass().getName() + ":" + field.getName());
        }
    }

    public static void copyPropertiesIgnoreNull(Object source, Object target) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();
        Set<String> emptyNames = new HashSet<String>();
        for (java.beans.PropertyDescriptor pd : pds) {
            if (src.getPropertyValue(pd.getName()) == null) {
                emptyNames.add(pd.getName());
            }
        }
        String[] ignore = new String[emptyNames.size()];
        BeanUtils.copyProperties(source, target, ignore);
    }

    /**
     * 执行get方法
     *
     * @param field
     * @param obj
     * @return
     */
  /*  public static Object invokeGetMethod(Field field, Object obj) {
        return invokeGetMethod(field.getName(), obj);
    }


    public static Object invokeGetMethod(String fieldName, Object obj) {

        Object ret;
        try {
            PropertyDescriptor pd = new PropertyDescriptor(fieldName, obj.getClass());
            Method getMethod = pd.getReadMethod();
            ret = getMethod.invoke(obj);
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("invokeGetMethod error:" + obj.getClass().getName() + ":" + fieldName);

        }
        return ret;
    }
    public static void invokeSetMethod(Field field, Object value, Object obj) {
        invokeSetMethod(field.getName(), value, obj);
    }


    public static void invokeSetMethod(String fieldName, Object value, Object obj) {
        try {
            PropertyDescriptor pd = new PropertyDescriptor(fieldName, obj.getClass());
            Method setMethod = pd.getWriteMethod();
            setMethod.invoke(obj, value);
        } catch (IntrospectionException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("invokeSetMethod error:" + obj.getClass().getName() + ":" + fieldName);

        }
    }*/


}
