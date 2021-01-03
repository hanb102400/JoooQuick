package com.shawn.jooo.framework.utils;

import org.apache.commons.lang3.StringUtils;



/**
 * redis key 工具
 *
 * @author shawn
 */
public class RedisKey {

    public static String getKey(String... keys) {
        return AppUtils.getPrefix() + "." + StringUtils.join(keys,".");
    }
}
