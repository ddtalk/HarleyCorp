package com.alibaba.dingtalk.openapi.springbootdemo.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "dingtalk.env", ignoreUnknownFields = true, exceptionIfInvalid = true)
public class DTalkProperties {
    private String openApiHost = "https://oapi.dingtalk.com";
    private String oasBgUrl;
    private String corpId;
    private String corpSecret;
    private String ssoSecret;
    private String suiteTicket;
    private String authCode;
    private String suiteToken;
    private String createSuiteKey;
    private String suiteKey;
    private String suiteSecret;
    private String token;
    private String encodingAesKey;
    private String agentId;
    private long cacheTime;
}
