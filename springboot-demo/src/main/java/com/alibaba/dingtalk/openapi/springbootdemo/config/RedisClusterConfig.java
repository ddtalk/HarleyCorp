package com.alibaba.dingtalk.openapi.springbootdemo.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;

@Configuration
public class RedisClusterConfig {

    @Autowired
    private RedisProperties redisProperties;


    @Bean(name = "jedisConnectionFactory")
    @ConditionalOnProperty(value = "spring.redis.cluster.enabled", havingValue = "Y")
    public JedisConnectionFactory jedisClusterConnectionFactory(){
        RedisClusterConfiguration redisClusterConfiguration = new RedisClusterConfiguration(redisProperties.getCluster().getNodes());
        redisClusterConfiguration.setMaxRedirects(redisProperties.getCluster().getMaxRedirects());
        return new JedisConnectionFactory(redisClusterConfiguration);
    }
}
