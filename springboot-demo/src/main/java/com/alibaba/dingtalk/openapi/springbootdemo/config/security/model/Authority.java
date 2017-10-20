package com.alibaba.dingtalk.openapi.springbootdemo.config.security.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Authority implements Serializable {
    private static final long serialVersionUID = -6133923444644049384L;
    private String name;
}
