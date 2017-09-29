package com.alibaba.dingtalk.openapi.springbootdemo.model.api;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

@lombok.Data
public class ApiPageableResp<T> implements Serializable {
    private static final long serialVersionUID = -8421200997970330114L;
    @JSONField(name = "success")
    private boolean success = false;
    @JSONField(name = "message")
    private String message;
    @JSONField(name = "data")
    private Data<T> data;
}
