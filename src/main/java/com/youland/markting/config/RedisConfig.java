package com.youland.markting.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializer;

@Configuration
@EnableConfigurationProperties({RedisProperties.class})
public class RedisConfig {
    private final RedisProperties redisProperties;

    @Autowired
    public RedisConfig(RedisProperties redisProperties) {
        this.redisProperties = redisProperties;
    }

    @Bean
    @Primary
    public RedisTemplate<?,?> redisTemplate(RedisConnectionFactory redisConnectionFactory) {
        return getRedisTemplate(redisConnectionFactory);
    }

    @Bean("zipCodeRedisTemplate")
    public RedisTemplate<?,?> zipCodeRedisTemplate() {
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration();
        redisStandaloneConfiguration.setHostName(redisProperties.getHost());
        redisStandaloneConfiguration.setDatabase(0);
        LettuceConnectionFactory redisConnectionFactory = new LettuceConnectionFactory(redisStandaloneConfiguration);
        redisConnectionFactory.afterPropertiesSet();
        return getRedisTemplate(redisConnectionFactory);
    }

    private RedisTemplate<?,?> getRedisTemplate(RedisConnectionFactory redisConnectionFactory) {
        RedisTemplate<?,?> template = new RedisTemplate<>();
        template.setConnectionFactory(redisConnectionFactory);
        // serializers
        template.setValueSerializer(RedisSerializer.json());
        template.setKeySerializer(RedisSerializer.string());
        template.setHashValueSerializer(RedisSerializer.string());
        template.setHashKeySerializer(RedisSerializer.string());

        return template;
    }

}
