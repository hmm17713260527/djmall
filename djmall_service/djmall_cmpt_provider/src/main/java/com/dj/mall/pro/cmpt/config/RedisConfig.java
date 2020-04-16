package com.dj.mall.pro.cmpt.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

/**
 * Redis 配置器
 */
@Configuration
public class RedisConfig {

    /**
     * 设置RedisTemplate
     *
     * @param redisConnectionFactory 连接池工厂
     * @return
     */
    @Bean
    public RedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate redisTemplate = new RedisTemplate();
        redisTemplate.setConnectionFactory(redisConnectionFactory);
        // Key 序列化-String
        redisTemplate.setKeySerializer(RedisSerializer.string());
        redisTemplate.setHashKeySerializer(RedisSerializer.string());
        // value序列化JSON
        FastJsonRedisSerializer fastJsonRedisSerializer = new FastJsonRedisSerializer(String.class);
        redisTemplate.setValueSerializer(fastJsonRedisSerializer);
//        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(String.class);
//        redisTemplate.setValueSerializer(jackson2JsonRedisSerializer);
        return redisTemplate;
    }
}
