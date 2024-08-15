package com.example.LibraryManagementSystem;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

// since we need to provide bean of RedisTemplate, so we use "@Configuration" annotation

@Configuration
public class RedisConfig {
    @Value("${redis.host}")
    String redisDataSource;

    @Value("${redis.port}")
    int redisPort;

    @Value("${redis.password}")
    String redisPassword;

    @Bean
    public LettuceConnectionFactory lettuceRedisConnectionFactory(){
        RedisStandaloneConfiguration redisStandaloneConfiguration = new RedisStandaloneConfiguration(redisDataSource, redisPort);
        redisStandaloneConfiguration.setPassword(redisPassword);

        return new LettuceConnectionFactory(redisStandaloneConfiguration);
    }

    @Bean
    public RedisTemplate<String, Object> getRedisTemplate(){
        RedisTemplate<String,Object> redisTemplate = new RedisTemplate<>();

        redisTemplate.setKeySerializer(new StringRedisSerializer()); // since we know that our key is string only
        redisTemplate.setValueSerializer(new JdkSerializationRedisSerializer());

        // serializer if we have key - value as HashMap
        redisTemplate.setHashKeySerializer(new JdkSerializationRedisSerializer());
        redisTemplate.setHashValueSerializer(new JdkSerializationRedisSerializer());

        redisTemplate.setConnectionFactory(lettuceRedisConnectionFactory());

        return redisTemplate;
    }
}
