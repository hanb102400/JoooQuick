package com.shawn.jooo.framework.utils;

import org.springframework.core.env.Environment;


/**
 * SpringPropertiesHolder工具类, 使用getProperty获取属性值
 *
 * @author shawn
 */
public class SpringConfigHolder {


    /**
     * @param name 属性名称
     * @return 属性值
     * @Title: getProperty
     * @Description: 根据属性名称，获取属性值
     */
    public static Object getProperty(final String name) {
        Environment env = SpringContextHolder.getBean(Environment.class);
        return env.getProperty(name);
    }

}