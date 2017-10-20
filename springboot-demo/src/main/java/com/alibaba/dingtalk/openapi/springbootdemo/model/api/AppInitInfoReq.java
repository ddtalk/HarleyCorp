package com.alibaba.dingtalk.openapi.springbootdemo.model.api;

import lombok.Data;

import java.io.Serializable;

@Data
public class AppInitInfoReq implements Serializable {
    private static final long serialVersionUID = 9036030781141485188L;
    private String url;
}
