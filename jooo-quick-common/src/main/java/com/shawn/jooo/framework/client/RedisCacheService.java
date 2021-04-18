package com.shawn.jooo.framework.client;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 *
 * @author shawn
 */
@Component
public class RedisCacheService {

    @Resource
    private StringRedisTemplate stringRedisTemplate;



}
