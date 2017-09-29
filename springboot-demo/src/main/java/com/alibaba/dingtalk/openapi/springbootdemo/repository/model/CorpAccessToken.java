package com.alibaba.dingtalk.openapi.springbootdemo.repository.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Data
@RedisHash("corpAccessTokens")
public class CorpAccessToken {
    @Id
    private String cortId;
    private String accessToken;
    private LocalDateTime beginTime;
}
