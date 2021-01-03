package com.shawn.jooo.framework.mybatis.reflect;

import org.apache.ibatis.type.JdbcType;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 *  Jdbc类型转换
 *
 * @author shawn
 */
public class JdbcTypes {

    protected static Map<Class<?>,String > typeMap = new HashMap();

    protected static Map<Class<?>,JdbcType> jdbcTypeMap = new HashMap();

    static {
        typeMap.put(Boolean.TYPE,"BOOLEAN");
        typeMap.put(Boolean.class,"BOOLEAN");
        typeMap.put(Byte.TYPE,"TINYINT");
        typeMap.put(Byte.class,"TINYINT");
        typeMap.put(Short.TYPE,"SMALLINT");
        typeMap.put(Short.class,"SMALLINT");
        typeMap.put(Integer.TYPE,"INTEGER");
        typeMap.put(Integer.class,"INTEGER");
        typeMap.put(Long.TYPE,"BIGINT");
        typeMap.put(Long.class,"BIGINT");
        typeMap.put(Float.TYPE,"FLOAT");
        typeMap.put(Float.class,"FLOAT");
        typeMap.put(Double.TYPE,"DOUBLE");
        typeMap.put(Double.class,"DOUBLE");
        typeMap.put(java.math.BigDecimal.class,"DECIMAL");

        typeMap.put(Character.TYPE,"CHAR");
        typeMap.put(Character.class,"CHAR");
        typeMap.put(String.class,"VARCHAR");
        typeMap.put(Date.class,"TIMESTAMP");
        typeMap.put(java.sql.Date.class,"TIMESTAMP");
        typeMap.put(java.sql.Time.class,"TIME");
        typeMap.put(Object.class,"ARRAY");
        typeMap.put(byte[].class,"BLOB");
        typeMap.put(Byte[].class,"BLOB");
    }


    static {
        jdbcTypeMap.put(Boolean.TYPE, JdbcType.BOOLEAN);
        jdbcTypeMap.put(Boolean.class,JdbcType.BOOLEAN);
        jdbcTypeMap.put(Byte.TYPE,JdbcType.TINYINT);
        jdbcTypeMap.put(Byte.class,JdbcType.TINYINT);
        jdbcTypeMap.put(Short.TYPE,JdbcType.SMALLINT);
        jdbcTypeMap.put(Short.class,JdbcType.SMALLINT);
        jdbcTypeMap.put(Integer.TYPE,JdbcType.INTEGER);
        jdbcTypeMap.put(Integer.class,JdbcType.INTEGER);
        jdbcTypeMap.put(Long.TYPE,JdbcType.BIGINT);
        jdbcTypeMap.put(Long.class, JdbcType.BIGINT);
        jdbcTypeMap.put(Float.TYPE,JdbcType.FLOAT);
        jdbcTypeMap.put(Float.class,JdbcType.FLOAT);
        jdbcTypeMap.put(Double.TYPE,JdbcType.DOUBLE);
        jdbcTypeMap.put(Double.class,JdbcType.DOUBLE);
        jdbcTypeMap.put(java.math.BigDecimal.class,JdbcType.DECIMAL);

        jdbcTypeMap.put(Character.TYPE, JdbcType.CHAR);
        jdbcTypeMap.put(Character.class,JdbcType.CHAR);
        jdbcTypeMap.put(String.class,JdbcType.VARCHAR);
        jdbcTypeMap.put(Date.class,JdbcType.TIMESTAMP);
        jdbcTypeMap.put(java.sql.Date.class,JdbcType.TIMESTAMP);
        jdbcTypeMap.put(java.sql.Time.class,JdbcType.TIME);
        jdbcTypeMap.put(Object.class,JdbcType.ARRAY);
        jdbcTypeMap.put(byte[].class,JdbcType.BLOB);
        jdbcTypeMap.put(Byte[].class,JdbcType.BLOB);

    }

    /**
     * 驼峰模式字符串转换为下划线字符串
     *
     * @param camelText  驼峰字符串
     * @return          下划线字符串
     */
    public static String getImprovedName(String camelText) {
        return camel2Underscore(camelText);
    }

    /**
     * 驼峰模式字符串转换为下划线字符串
     *
     * @param camelText  驼峰字符串
     * @return          下划线字符串
     */
    public static String camel2Underscore(String camelText) {
        if (camelText == null || camelText.trim().length() == 0) {
            return camelText;
        }
        StringBuilder out = new StringBuilder();
        char[] strChar = camelText.toCharArray();
        for (int i = 0, len = strChar.length; i < len; i++) {
            char c = strChar[i];
            if (Character.isUpperCase(c)) {
                //如果不是首字符，则需要添加分隔符
                if (i != 0) {
                    out.append("_");
                }
                out.append(Character.toLowerCase(c));
                continue;
            }
            out.append(c);
        }
        return out.toString();
    }



    public static String calculateJdbcTypeName(Class<?> javaType){
        return typeMap.get(javaType);
    }

    public static JdbcType calculateJdbcType(Class<?> javaType){
        return jdbcTypeMap.get(javaType);
    }

}
