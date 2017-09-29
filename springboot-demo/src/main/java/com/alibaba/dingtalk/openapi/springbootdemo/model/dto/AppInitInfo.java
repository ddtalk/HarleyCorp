package com.alibaba.dingtalk.openapi.springbootdemo.model.dto;

import lombok.Data;

@Data
public class AppInitInfo {
    private String nonceStr;
    private String timestamp;
    private String signature;
    private String corpId;
    private String agentId;
}
