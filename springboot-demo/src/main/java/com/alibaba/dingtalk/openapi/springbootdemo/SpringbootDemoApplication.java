package com.alibaba.dingtalk.openapi.springbootdemo;

import com.alibaba.dingtalk.openapi.springbootdemo.config.DTalkProperties;
import com.alibaba.dingtalk.openapi.springbootdemo.config.OasProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;

@SpringBootApplication
@EnableRedisRepositories(basePackages = "com.alibaba.dingtalk.openapi.springbootdemo.repository")
@EnableConfigurationProperties({OasProperties.class, RedisProperties.class, DTalkProperties.class})
public class SpringbootDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootDemoApplication.class, args);
    }
}
