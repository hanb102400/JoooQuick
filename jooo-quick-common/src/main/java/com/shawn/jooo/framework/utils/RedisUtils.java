package com.shawn.jooo.framework.utils;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

/**
 * resis工具类.
 *
 * @author shawn
 */
public class RedisUtils {


    public static StringRedisTemplate getStringRedisTemplate() {
        StringRedisTemplate stringRedisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
        return stringRedisTemplate;
    }

    public static String getKey(String... keys) {
        return RedisKey.getKey(keys);
    }

    public static String get(String key) {
        return getStringRedisTemplate().opsForValue().get(key);
    }

    public static void set(String key, String value) {
        getStringRedisTemplate().opsForValue().set(key, value);
    }

    public static Integer getInt(String key) {
        String val = getStringRedisTemplate().opsForValue().get(key);
        return NumberUtils.toInt(val);
    }

    public static void setInt(String key, Integer value) {
        getStringRedisTemplate().opsForValue().set(key, String.valueOf(value));
    }

    public static Long increment(String key) {
        return getStringRedisTemplate().opsForValue().increment(key);
    }

    public static Long increment(String key, Long num) {
        return getStringRedisTemplate().opsForValue().increment(key, num);
    }


    public void set(String key, String value, long expireSec) {
        getStringRedisTemplate().opsForValue().set(key, value, expireSec, TimeUnit.SECONDS);
    }

    public void setExpire(String key, long expireSec) {
        getStringRedisTemplate().expire(key, expireSec, TimeUnit.SECONDS);
    }

    public void delete(String key) {
        getStringRedisTemplate().delete(key);
    }

    public Long incr(String key, long delta) {
        return getStringRedisTemplate().opsForValue().increment(key, delta);
    }

    public void lpush(String key, String value) {
        getStringRedisTemplate().opsForList().leftPush(key, value);
    }

    public String rpop(String key) {
        return getStringRedisTemplate().opsForList().rightPop(key);
    }

    public void hset(String h, String hk, Object hv) {
        getStringRedisTemplate().opsForHash().put(h, hk, hv);
    }

    public Object hget(String h, String hk) {
        return getStringRedisTemplate().opsForHash().get(h, hk);
    }


    public String scriptLoad(String script) {
        return getStringRedisTemplate().execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.scriptLoad(script.getBytes());
            }
        });
    }

    public String evalSha(String sha, int numKeys, byte[]... args) {
        return getStringRedisTemplate().execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.evalSha(sha, ReturnType.VALUE, numKeys, args);
            }
        });
    }

    public void scriptFlush() {
        getStringRedisTemplate().execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.scriptFlush();
                return null;
            }
        });
    }
}
