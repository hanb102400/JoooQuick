package com.shawn.jooo.framework.client;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.ReturnType;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author shawn
 */
@Component
public class RedisCacheService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;




}
