package com.alibaba.dingtalk.openapi.springbootdemo.config.security.dingtalk;

import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

public class DTalkConfigurer extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    public static final String PARAMETER_CODE = "code";

    private DTalkSecurityModule dTalkSecurityModule;

    public DTalkConfigurer(DTalkSecurityModule dTalkSecurityModule) {
        this.dTalkSecurityModule = dTalkSecurityModule;
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        DTalkFilter dTalkFilter = new DTalkFilter(dTalkSecurityModule);
        http.addFilterBefore(dTalkFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
