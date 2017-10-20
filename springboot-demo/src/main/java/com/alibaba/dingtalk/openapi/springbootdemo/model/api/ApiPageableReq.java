package com.alibaba.dingtalk.openapi.springbootdemo.model.api;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Data;


@Data
public class ApiPageableReq<T> {
    @JSONField(name = "req")
    private T req;
    @JSONField(name = "pageSize")
    private Integer pageSize;
    @JSONField(name = "pages")
    private Integer pages;
}
