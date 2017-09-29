package com.alibaba.dingtalk.openapi.springbootdemo.config.security.dingtalk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class DTalkSecurityModule {
    public boolean validateCode(String code) {
        return false;
    }

    public Authentication getAuthentication(String code) {
        return null;
    }
}
