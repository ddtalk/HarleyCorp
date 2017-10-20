package com.alibaba.dingtalk.openapi.springbootdemo.config.security.model;

public enum AuthenticationType {
    OA_LOGIN(0), DING_TALK_CODE(1);
    private Integer code;

    AuthenticationType(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }
}
