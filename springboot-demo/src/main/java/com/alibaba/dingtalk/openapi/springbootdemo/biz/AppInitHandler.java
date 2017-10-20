package com.alibaba.dingtalk.openapi.springbootdemo.biz;

import com.alibaba.dingtalk.openapi.springbootdemo.integration.user.DTalkUserService;
import com.alibaba.dingtalk.openapi.springbootdemo.model.dto.DTalkUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AppInitHandler {

    @Autowired
    private DTalkUserService dTalkUserService;

    public DTalkUser getDTalkUserByCode(String code) {
        return null;
    }
}
