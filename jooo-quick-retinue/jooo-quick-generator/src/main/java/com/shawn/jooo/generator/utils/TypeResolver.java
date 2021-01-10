package com.shawn.jooo.generator.utils;


import com.shawn.jooo.generator.xstream.Convert;
import com.thoughtworks.xstream.XStream;

import java.io.InputStream;
import java.lang.reflect.Field;
import java.sql.Types;

/**
 *
 * @author shawn
 */
public class TypeResolver {

    private static Convert convert;

    static {
        XStream xstream = new XStream();
        xstream.processAnnotations( Convert.class);
        String configFile = "types.xml";
        InputStream in = Thread.currentThread().getContextClassLoader().getResourceAsStream(configFile);
        convert = (Convert) xstream.fromXML(in);

    }


    /**
     * sql类型转为jdbc类型
     */
    public static String convertToJdbcType(int dataType, int size) {
        String sqlType = convert.getTypes().stream().filter(e -> {
            try {
                return dataType == TypeResolver.getSqlTypeNumber(e.getJdbcType());
            } catch (NoSuchFieldException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
            return false;
        }).map(e -> e.getJdbcType()).findFirst().orElseThrow(() -> new RuntimeException(""));
        return sqlType;
    }

    /**
     * sql类型转为java的类型名称（不包含包前缀）
     */
    public static String convertToJavaType(int dataType, int size) {
        String javaType = convert.getTypes().stream().filter(e -> {
            try {
                return dataType == TypeResolver.getSqlTypeNumber(e.getJdbcType());
            } catch (NoSuchFieldException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
            return false;
        }).map(e -> e.getJavaType()).findFirst().orElseThrow(() -> new RuntimeException(""));
        return javaType;
    }

    /**
     * sql类型转为java类型（包含包前缀）
     */
    public static String convertToJavaTypeFull(int dataType, int size) {
        String javaType = convert.getTypes().stream().filter(e -> {
            try {
                return dataType == TypeResolver.getSqlTypeNumber(e.getJdbcType());
            } catch (NoSuchFieldException e1) {
                e1.printStackTrace();
            } catch (IllegalAccessException e1) {
                e1.printStackTrace();
            }
            return false;
        }).map(e -> e.getJavaTypeFull()).findFirst().orElseThrow(() -> new RuntimeException(""));
        return javaType;
    }

    private static int getSqlTypeNumber(String sqlType) throws NoSuchFieldException, IllegalAccessException {
        Field filed = Types.class.getDeclaredField(sqlType);
        filed.setAccessible(true);
        return (int) filed.get(null);
    }


}
