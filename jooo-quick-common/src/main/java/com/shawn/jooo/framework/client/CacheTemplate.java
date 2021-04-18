package com.shawn.jooo.framework.client;

import com.shawn.jooo.framework.utils.JSONUtils;
import com.shawn.jooo.framework.utils.RedisKey;
import com.shawn.jooo.framework.utils.RedisUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Type;
import java.time.Duration;
import java.util.concurrent.Callable;

/**
 * 缓存工具类，默认缓存10分钟
 *
 * @author shawn
 */
@Component

public class CacheTemplate {

    private Logger logger = LoggerFactory.getLogger(CacheTemplate.class);



    private Type typeOfT;

    private String key;

    private String field;

    private Callable callable;

    private Long expire;

    public static CacheTemplate create() {
        return new CacheTemplate();
    }

    public CacheTemplate key(String key) {
        this.key = key;
        return this;
    }

    public CacheTemplate field(String field) {
        this.field = field;
        return this;
    }

    public CacheTemplate expire(int exipre) {
        this.expire = expire;
        return this;
    }

    public CacheTemplate value(Callable callable) {
        this.callable = callable;
        return this;
    }

    public CacheTemplate type(Type typeOfT) {
        this.typeOfT = typeOfT;
        return this;
    }

    public CacheTemplate type(Class<?> classOfT) {
        this.typeOfT = ((Type) (classOfT));
        return this;
    }

    public <T> T get() {
        if (StringUtils.isEmpty(key)) {
            throw new RuntimeException("key must be set value!");
        }
        if (typeOfT == null) {
            throw new RuntimeException("type must be set value!");
        }
        if (callable == null) {
            throw new RuntimeException("data call must be set!");
        }
        if (expire == null) {
            expire = RedisKey.getDefaultExpireTime();
        }
        String redisKey = RedisKey.getKey(key);
        if (StringUtils.isEmpty(field)) {
            try {
                String result = RedisUtils.get(redisKey);
                if (StringUtils.isNotEmpty(result)) {
                    logger.debug("[cache] get cache from redis : {} = {}", redisKey, result);
                    return JSONUtils.parseObject(result, typeOfT);
                } else {
                    Object callResult = callable.call();
                    if (!ObjectUtils.isEmpty(callResult)) {
                        result = JSONUtils.toJSONString(callResult);
                        logger.debug("[cache] get cache from database : {} = {}, expire {}", redisKey, result, expire);
                        RedisUtils.set(redisKey, result);
                        if (expire != -1) {
                            RedisUtils.expire(redisKey, Duration.ofSeconds(expire));
                        }
                        return ((T) callResult);
                    }
                }
                return null;
            } catch (Exception e) {
                logger.warn("[cache] get cache error key = {},  msg = {} \n", redisKey, e.getMessage());
                logger.error("[cache] error", e);
            }
        } else {
            try {
                String result = RedisUtils.hget(redisKey, field);
                if (StringUtils.isNotEmpty(result)) {
                    logger.debug("[cache] get cache from redis : {}.{} = {}", redisKey, field, result);
                    return JSONUtils.parseObject(result, typeOfT);
                } else {
                    Object callResult = callable.call();
                    if (!ObjectUtils.isEmpty(callResult)) {
                        result = JSONUtils.toJSONString(callResult);
                        logger.debug("[cache] get cache from database : {}.{} = {}, expire {}", redisKey, field, result);
                        if (RedisUtils.persist(redisKey)) {
                            RedisUtils.hset(redisKey, field, result);
                        } else {
                            RedisUtils.hset(redisKey, field, result);
                            if (expire != -1) {
                                RedisUtils.expire(redisKey, Duration.ofSeconds(expire));
                            }
                        }
                        return ((T) callResult);
                    }
                }
                return null;
            } catch (Exception e) {
                logger.warn("[cache] get cache error key = {}, field = {}, msg = {} \n", redisKey, field, e.getMessage());
                logger.error("[cache] error", e);
            }
            return null;
        }

        return null;
    }

    public void clear() {
        if (StringUtils.isEmpty(key)) {
            throw new RuntimeException("key must be set value!");
        }
        String redisKey = RedisKey.getKey(key);
        if (StringUtils.isEmpty(field)) {
            RedisUtils.del(redisKey);
        } else {
            RedisUtils.hdel(redisKey, field);
        }
    }
}
