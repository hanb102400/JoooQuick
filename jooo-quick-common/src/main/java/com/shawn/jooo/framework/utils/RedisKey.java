package com.shawn.jooo.framework.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.env.Environment;


/**
 * redis key
 *
 * @author shawn
 */
public class RedisKey {

    public static String getKey(String... keys) {
        return getPrefix() + ":" + StringUtils.join(keys, ":");
    }

    public static Long getDefaultExpireTime() {
        Environment env = SpringContextHolder.getBean(Environment.class);
        if (env != null && StringUtils.isNotBlank(env.getProperty("app.cache.expireTime"))) {
            return Long.parseLong(env.getProperty("app.cache.expireTime"));
        }
        return 0L;
    }

    private static String getPrefix() {
        Environment env = SpringContextHolder.getBean(Environment.class);
        if (env != null && StringUtils.isNotBlank(env.getProperty("app.cache.prefix"))) {
            return env.getProperty("app.cache.prefix");
        } else if (env != null && StringUtils.isNotBlank(env.getProperty("app.prefix"))) {
            return env.getProperty("app.prefix");
        }
        return "_";
    }
}
