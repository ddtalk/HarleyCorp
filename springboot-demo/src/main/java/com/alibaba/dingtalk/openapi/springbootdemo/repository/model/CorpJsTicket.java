package com.alibaba.dingtalk.openapi.springbootdemo.repository.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@Data
@RedisHash("corpJsTickets")
public class CorpJsTicket {
    @Id
    private String cortId;
    private String ticket;
    private LocalDateTime beginTime;
}
