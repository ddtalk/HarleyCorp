package com.alibaba.dingtalk.openapi.springbootdemo.model.api;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;
import java.util.List;

@lombok.Data
public class Data<R>  implements Serializable {
    @JSONField(name = "total")
    private Integer total;
    @JSONField(name = "currentPage")
    private Integer currentPage;
    @JSONField(name = "pageSize")
    private Integer pageSize;
    @JSONField(name = "pages")
    private Integer pages;
    @JSONField(name = "dataList")
    private List<R> dataList;
}
