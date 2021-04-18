package com.shawn.jooo.framework.utils;

import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.time.Duration;
import java.util.concurrent.TimeUnit;

/**
 * resis工具类.
 *
 * @author shawn
 */
public class RedisUtils {

    public static StringRedisTemplate getTemplate() {
        StringRedisTemplate stringRedisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
        return stringRedisTemplate;
    }

    public static Boolean persist(String key) {
        return getTemplate().persist(key);
    }

    public static String getKey(String... keys) {
        return RedisKey.getKey(keys);
    }

    public static String get(String key) {
        return getTemplate().opsForValue().get(key);
    }

    public static void set(String key, String value) {
        getTemplate().opsForValue().set(key, value);
    }

    public static Integer getInt(String key) {
        String val = getTemplate().opsForValue().get(key);
        return NumberUtils.toInt(val);
    }

    public static Long getLong(String key) {
        String val = getTemplate().opsForValue().get(key);
        return NumberUtils.toLong(val);
    }

    public static void setInt(String key, Integer value) {
        getTemplate().opsForValue().set(key, String.valueOf(value));
    }

    public static Long increment(String key) {
        return getTemplate().opsForValue().increment(key);
    }

    public static Long increment(String key, Long num) {
        return getTemplate().opsForValue().increment(key, num);
    }

    public static void set(String key, String value, long expireSec) {
        getTemplate().opsForValue().set(key, value, expireSec, TimeUnit.SECONDS);
    }

    public static void expire(String key, long expireSec) {
        getTemplate().expire(key, expireSec, TimeUnit.SECONDS);
    }

    public static void expire(String key, Duration duration) {
        getTemplate().expire(key, duration);
    }

    public static void del(String key) {
        getTemplate().delete(key);
    }

    public static Long incr(String key, long delta) {
        return getTemplate().opsForValue().increment(key, delta);
    }

    public static void lpush(String key, String value) {
        getTemplate().opsForList().leftPush(key, value);
    }

    public static String rpop(String key) {
        return getTemplate().opsForList().rightPop(key);
    }

    public static void hset(String h, String hk, Object hv) {
        getTemplate().opsForHash().put(h, hk, hv);
    }

    public static String hget(String h, String hk) {
        return getTemplate().opsForHash().get(h, hk).toString();
    }

    public static String hdel(String h, String hk) {
        return getTemplate().opsForHash().delete(h, hk).toString();
    }

    public String scriptLoad(String script) {
        return getTemplate().execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.scriptLoad(script.getBytes());
            }
        });
    }

    public String evalSha(String sha, int numKeys, byte[]... args) {
        return getTemplate().execute(new RedisCallback<String>() {
            @Override
            public String doInRedis(RedisConnection redisConnection) throws DataAccessException {
                return redisConnection.evalSha(sha, ReturnType.VALUE, numKeys, args);
            }
        });
    }

    public void scriptFlush() {
        getTemplate().execute(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.scriptFlush();
                return null;
            }
        });
    }
}
