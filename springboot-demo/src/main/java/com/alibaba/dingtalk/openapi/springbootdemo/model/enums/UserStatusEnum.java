package com.alibaba.dingtalk.openapi.springbootdemo.model.enums;

import java.util.Optional;
import java.util.stream.Stream;

public enum UserStatusEnum {
    INACTIVATED("0"), ACTIVATED("1"), EXPIRED("2");
    private String code;

    UserStatusEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public static Optional<UserStatusEnum> getByCode(String code) {
        return Stream.of(values()).filter(e -> e.getCode().equals(code)).findFirst();
    }
}
