package com.shawn.jooo.framework.utils;


import com.shawn.jooo.framework.config.HttpClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.RedisStringCommands;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.types.Expiration;

import java.util.concurrent.TimeUnit;


/**
 * 分布式锁工具
 *
 * @author shawn
 */
public class RedisLock {

    private static Logger logger = LoggerFactory.getLogger(RedisLock.class);

    private static StringRedisTemplate stringRedisTemplate;

    static {
        stringRedisTemplate = SpringContextHolder.getBean(StringRedisTemplate.class);
    }

    private static final String REDIS_DELKEY_SCRIPT = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";


    /**
     * 尝试获取分布式锁
     * 立即返回，不会等待
     *
     * <p>
     * SET resource_name my_random_value NX PX 10000 保持原子性
     * </p>
     *
     * @param lockKey    锁
     * @param clientId   客户端标识(采用UUID)
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean tryLock(String lockKey, String clientId, long expireTime) {
        boolean success = stringRedisTemplate.execute((RedisCallback<Boolean>) connection ->
                connection.set(lockKey.getBytes(), clientId.getBytes(), Expiration.from(expireTime, TimeUnit.SECONDS), RedisStringCommands.SetOption.SET_IF_ABSENT));
        return success;
    }

    /**
     * 尝试获取分布式锁
     * 立即返回，不会等待
     *
     * <p>
     * SET resource_name my_random_value NX PX 10000 保持原子性
     * </p>
     *
     * @param lockKey    锁
     * @param clientId   客户端标识(采用UUID)
     * @param expireTime 超期时间
     * @return 是否获取成功
     */
    public static boolean tryLock(String lockKey, String clientId, long expireTime, TimeUnit timeUnit) {
        boolean success = stringRedisTemplate.execute((RedisCallback<Boolean>) connection ->
                connection.set(lockKey.getBytes(), clientId.getBytes(), Expiration.from(expireTime, timeUnit), RedisStringCommands.SetOption.SET_IF_ABSENT));
        return success;
    }

    /**
     * 尝试获取分布式锁,
     * 持续尝试获取，阻塞等待直到获取为止
     *
     * <p>
     * SET resource_name my_random_value NX PX 10000 保持原子性
     * </p>
     *
     * @param lockKey        锁
     * @param clientId       请求标识
     * @param expireTime     超期时间
     * @param acquireTimeout 尝试时间
     * @return 是否获取成功
     */
    public static boolean waitLock(String lockKey, String clientId, long expireTime, long acquireTimeout) {
        boolean success = false;
        long start = System.currentTimeMillis();
        do {
            success = stringRedisTemplate.execute((RedisCallback<Boolean>) connection ->
                    connection.set(lockKey.getBytes(), clientId.getBytes(), Expiration.from(expireTime, TimeUnit.SECONDS), RedisStringCommands.SetOption.SET_IF_ABSENT));
            if (success) {
                return true;
            }
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                logger.error("InterruptedException",e);
                Thread.currentThread().interrupt();
            }
        } while (System.currentTimeMillis() < start + acquireTimeout);
        return false;
    }


    /**
     * 释放分布式锁
     * <p>
     * 使用LUA脚本保持原子性
     *
     * @param lockKey  锁
     * @param clientId 请求标识
     * @return 是否释放成功
     */
    public static boolean releaseLock(String lockKey, String clientId) {

        Long ok = stringRedisTemplate.execute((RedisCallback<Long>) connection ->
                connection.eval(REDIS_DELKEY_SCRIPT.getBytes(), ReturnType.INTEGER, 1, lockKey.getBytes(), String.valueOf(clientId).getBytes()));
        return 1 == ok ? true : false;
    }

}
