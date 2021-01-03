package com.shawn.jooo.framework.mybatis.reflect;

import org.apache.commons.lang3.StringUtils;

import javax.persistence.Column;
import java.beans.Introspector;
import java.lang.invoke.SerializedLambda;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.regex.Pattern;


/**
 *  Fn函数映射工具
 *
 * @author shawn
 */
public class FnReflections {

    private static final Pattern GET_PATTERN = Pattern.compile("^get[A-Z].*");
    private static final Pattern IS_PATTERN = Pattern.compile("^is[A-Z].*");

    private FnReflections() {
    }

    public static String fnToFieldName(Fn fn) {
        try {
            Method method = fn.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(fn);
            String getter = serializedLambda.getImplMethodName();
            String className = serializedLambda.getImplClass();

            if (GET_PATTERN.matcher(getter).matches()) {
                getter = getter.substring(3);
            } else if (IS_PATTERN.matcher(getter).matches()) {
                getter = getter.substring(2);
            }
            return Introspector.decapitalize(getter);
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("get field by static Function method error:" + fn.getClass().getName(), e);
        }
    }
	
	public static String fnToColumnName(Fn fn) {
         try {
            Method method = fn.getClass().getDeclaredMethod("writeReplace");
            method.setAccessible(Boolean.TRUE);
            SerializedLambda serializedLambda = (SerializedLambda) method.invoke(fn);
            String getter = serializedLambda.getImplMethodName();
            String className = serializedLambda.getImplClass();

            if (GET_PATTERN.matcher(getter).matches()) {
                getter = getter.substring(3);
            } else if (IS_PATTERN.matcher(getter).matches()) {
                getter = getter.substring(2);
            }
            return fieldNameToColumn(className, Introspector.decapitalize(getter));
        } catch (ReflectiveOperationException e) {
            throw new RuntimeException("get field by static Function method error:" + fn.getClass().getName(), e);
        }
    }

    private static String fieldNameToColumn(String className, String fieldName) throws ClassNotFoundException, NoSuchFieldException {
        className = StringUtils.replaceChars(className, '/', '.');
        Class clazz = Class.forName(className);
        Field filed = clazz.getDeclaredField(fieldName);
        String columnName = fieldToCloumnName(clazz, filed);
        return columnName;
    }


    private static String fieldToCloumnName(Class clazz, Field field) {
        String columnName;
        if (field.isAnnotationPresent(Column.class) && field.getAnnotation(Column.class).name() !=null) {
            columnName = field.getAnnotation(Column.class).name();
        } else {
            columnName = field.getName();
        }
        return columnName;
    }
}