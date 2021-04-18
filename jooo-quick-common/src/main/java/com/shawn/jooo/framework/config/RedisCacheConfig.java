package com.shawn.jooo.framework.config;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.impl.LaissezFaireSubTypeValidator;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisPassword;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * @author shawn
 */
@Configuration
@EnableCaching
@ConditionalOnClass(JedisPoolConfig.class)
public class RedisCacheConfig extends CachingConfigurerSupport {

    private static final Logger logger = LoggerFactory.getLogger(RedisCacheConfig.class);

    @Value("${spring.redis.host:}")
    private String host;

    @Value("${spring.redis.port:6379}")
    private Integer port;

    @Value("${spring.redis.password:}")
    private String password;

    @Value("${spring.redis.database:0}")
    private Integer database;

    @Value("${spring.redis.jedis.pool.max-active:8}")
    private Integer maxActive;

    @Value("${spring.redis.jedis.pool.max-idle:8}")
    private Integer maxIdle;

    @Value("${spring.redis.jedis.pool.max-wait:-1}")
    private Long maxWait;

    @Value("${spring.redis.jedis.pool.min-idle:0}")
    private Integer minIdle;

    @Value("${app.cache.prefix}")
    private String CACHE_PREFIX;

    @Value("${app.cache.expireTime}")
    private long CACHE_EXPIRE_TIME;

    private static StringRedisSerializer getStringRedisSerializer(){
        return new StringRedisSerializer();
    }

    private static Jackson2JsonRedisSerializer getJsonRedisSerializer(){
        // 定义Jackson2JsonRedisSerializer序列化对象
        Jackson2JsonRedisSerializer<Object> jsonRedisSerializer = new Jackson2JsonRedisSerializer<>(Object.class);
        ObjectMapper om = new ObjectMapper();
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        //POJO无public的属性或方法时，不报错
        om.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
        // null值字段不显示
        om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        // 序列化JSON串时，在值上打印出对象类型
        om.activateDefaultTyping(LaissezFaireSubTypeValidator.instance ,ObjectMapper.DefaultTyping.NON_FINAL, JsonTypeInfo.As.WRAPPER_ARRAY);
        jsonRedisSerializer.setObjectMapper(om);
        return  jsonRedisSerializer;
    }

    @Bean
    public RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory factory) {
        //配置序列化方式
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(factory);
        template.setKeySerializer(getStringRedisSerializer());
        template.setValueSerializer(getJsonRedisSerializer());
        template.setHashKeySerializer(getStringRedisSerializer());
        template.setHashValueSerializer(getJsonRedisSerializer());
        template.afterPropertiesSet();
        return template;
    }

    /**
     * 缓存配置管理器
     */
    @Bean
    public CacheManager cacheManager(RedisTemplate redisTemplate,RedisConnectionFactory redisConnectionFactory) {
        RedisCacheManager cacheManager = RedisCacheManager.builder(RedisCacheWriter.nonLockingRedisCacheWriter(redisConnectionFactory))
                //默认的缓存配置(没有配置键的key均使用此配置)
                .cacheDefaults(getDefaultCacheConfiguration())
                //自定义缓存配置
                .withInitialCacheConfigurations(getCustomCacheConfigurations())
                //在spring事务正常提交时才缓存数据
                .transactionAware()
                .build();
        return cacheManager;


    }

    /**
     * 默认缓存配置
     *
     * @return
     */
    public RedisCacheConfiguration getDefaultCacheConfiguration() {
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration
                .entryTtl(Duration.ofSeconds(CACHE_EXPIRE_TIME))
                .computePrefixWith(cacheKeyPrefix());
        return redisCacheConfiguration;
    }

    /**
     * 自定义缓存配置
     *
     * @return
     */
    public Map<String, RedisCacheConfiguration> getCustomCacheConfigurations() {
        Map<String, RedisCacheConfiguration> configurationMap = new HashMap<>();
        RedisCacheConfiguration redisCacheConfiguration = RedisCacheConfiguration.defaultCacheConfig();
        redisCacheConfiguration = redisCacheConfiguration
                .entryTtl(Duration.ofSeconds(CACHE_EXPIRE_TIME))
                .computePrefixWith(cacheKeyPrefix());
        configurationMap.put("custom", redisCacheConfiguration);
        return configurationMap;
    }


    @Bean
    public JedisPoolConfig jedisPool() {
        JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
        jedisPoolConfig.setMaxIdle(maxIdle);
        jedisPoolConfig.setMaxWaitMillis(maxWait);
        jedisPoolConfig.setMaxTotal(maxActive);
        jedisPoolConfig.setMinIdle(minIdle);
        return jedisPoolConfig;
    }

    @Bean
    public RedisStandaloneConfiguration jedisConfig() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration();
        config.setHostName(host);
        config.setPort(port);
        config.setDatabase(database);
        config.setPassword(RedisPassword.of(password));
        return config;
    }

    @Bean
    public RedisConnectionFactory redisConnectionFactory(JedisPoolConfig jedisPoolConfig, RedisStandaloneConfiguration jedisConfig) {
        JedisClientConfiguration.JedisPoolingClientConfigurationBuilder jpcb =
                (JedisClientConfiguration.JedisPoolingClientConfigurationBuilder) JedisClientConfiguration.builder();
        jpcb.poolConfig(jedisPoolConfig);
        JedisClientConfiguration jedisClientConfiguration = jpcb.build();
        return new JedisConnectionFactory(jedisConfig, jedisClientConfiguration);
    }

    /**
     * 自定义缓存key的生成策略。
     *
     * @return
     */
    @Bean
    @Override
    public KeyGenerator keyGenerator() {
        return (Object target, Method method, Object... params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName()).append(":");
            sb.append(method.getName());
            for (Object obj : params) {
                sb.append("&").append(obj.toString());
            }
            String cacheKey = DigestUtils.sha256Hex(sb.toString());
            logger.debug("cache key:::{}", cacheKey);
            return cacheKey;
        };
    }

    @Bean
    public CacheKeyPrefix cacheKeyPrefix() {
        return (cacheName) -> {
            return CACHE_PREFIX + ":" + cacheName + ":";
        };
    }
}
