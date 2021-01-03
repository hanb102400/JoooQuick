package com.shawn.jooo.framework.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;

public class AppUtils {

    public static String getPrefix() {
        Environment env = SpringContextHolder.getBean(Environment.class);
        if (env != null && StringUtils.isNotBlank(env.getProperty("app.prefix"))) {
            return env.getProperty("app.prefix");
        }
        return "_";
    }
}
