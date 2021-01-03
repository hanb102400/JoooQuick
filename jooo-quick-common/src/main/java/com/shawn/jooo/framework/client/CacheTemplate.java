package com.shawn.jooo.framework.client;

import com.shawn.jooo.framework.utils.JSONUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.lang.reflect.Type;
import java.time.Duration;
import java.util.concurrent.Callable;

/**
 * 缓存工具类，默认缓存10分钟
 *
 * @author shawn
 */
@Component
@ConditionalOnClass(StringRedisTemplate.class)
public class CacheTemplate {

    private Logger logger = LoggerFactory.getLogger(CacheTemplate.class);

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    private Type typeOfT;

    private String key;

    private String field;

    private Callable callable;

    @Value("${app.cacheExpireTime:600}")
    private int expire;

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

        if (StringUtils.isEmpty(field)) {
            try {
                String result = stringRedisTemplate.opsForValue().get(key);
                if (StringUtils.isNotEmpty(result)) {
                    logger.debug("[cache] get client from redis : {} = {}", key, result);
                    return JSONUtils.parseObject(result, typeOfT);
                } else {
                    Object callResult = callable.call();
                    if (!ObjectUtils.isEmpty(callResult)) {
                        result = JSONUtils.toJSONString(callResult);
                        logger.debug("[client] get client from database : {} = {}, expire {}", key, result, expire);
                        stringRedisTemplate.opsForValue().set(key, result);
                        if (expire != -1) {
                            stringRedisTemplate.expire(key, Duration.ofSeconds(expire));
                        }
                        return ((T) callResult);
                    }
                }
                return null;
            } catch (Exception e) {
                logger.error("[cache] CacheTemplate error key = {},  msg = {} \n", key, e.getMessage());
            }
        } else {
            try {
                String result = (String) stringRedisTemplate.opsForHash().get(key, field);
                if (StringUtils.isNotEmpty(result)) {
                    logger.debug("[cache] get client from redis : {}.{} = {}", key, field, result);
                    return JSONUtils.parseObject(result, typeOfT);
                } else {
                    Object callResult = callable.call();
                    if (!ObjectUtils.isEmpty(callResult)) {
                        result = JSONUtils.toJSONString(callResult);
                        logger.debug("[cache] get client from database : {}.{} = {}, expire {}", key, field, result);
                        if (stringRedisTemplate.persist(key)) {
                            stringRedisTemplate.opsForHash().put(key, field, result);
                        } else {
                            stringRedisTemplate.opsForHash().put(key, field, result);
                            if (expire != -1) {
                                stringRedisTemplate.expire(key, Duration.ofSeconds(expire));
                            }
                        }
                        return ((T) callResult);
                    }
                }
                return null;
            } catch (Exception e) {
                logger.error("[cache] CacheTemplate error key = {}, field = {}, msg = {} \n", key, field, e.getMessage());
            }
            return null;
        }

        return null;
    }
}
