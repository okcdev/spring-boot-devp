
package com.rbs.cn.rest.utils.redis.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisConfig {
    static Logger logger = LoggerFactory.getLogger(RedisConfig.class);

    /**
     * 替换掉原有的redisTemplate，主要设置ValueSerializer为JacksonSerializer，以便于使用
     * @param redisConnectionFactory 连接工厂
     * @return 修改后的redisTemplate
     */
    @Bean
    public RedisTemplate<String, String> redisTemplate(RedisConnectionFactory redisConnectionFactory){
        StringRedisTemplate stringRedisTemplate = new StringRedisTemplate(redisConnectionFactory);
        stringRedisTemplate.setKeySerializer(new StringRedisSerializer());
        stringRedisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());
        stringRedisTemplate.setHashValueSerializer(new GenericJackson2JsonRedisSerializer());
        stringRedisTemplate.setHashKeySerializer(new StringRedisSerializer());
        return stringRedisTemplate;
    }
}
