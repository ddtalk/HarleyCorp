package com.alibaba.dingtalk.openapi.springbootdemo.model.api;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;

@Data
public class ApiResp<T> {
    @JSONField(name = "success")
    private boolean success = false;
    @JSONField(name = "message")
    private String message;
    @JSONField(name = "data")
    private T data;
}
