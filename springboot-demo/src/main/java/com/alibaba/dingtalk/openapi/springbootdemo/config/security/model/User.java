package com.alibaba.dingtalk.openapi.springbootdemo.config.security.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

@Data
public class User implements Serializable {
    private static final long serialVersionUID = 3359961589595632880L;
    private String username;
    private transient byte[] password;
    private String empNo;
    private String dTalkUserId;
    private boolean activated = false;
    private Set<Authority> authorities;
}
