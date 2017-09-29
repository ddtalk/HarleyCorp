package com.alibaba.dingtalk.openapi.springbootdemo.config.security.dingtalk;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filters incoming requests and installs a Spring Security principal if a
 * header corresponding to a valid user is found.
 */
@Slf4j
public class DTalkFilter extends GenericFilterBean {

    private DTalkSecurityModule dTalkSecurityModule;

    public DTalkFilter(DTalkSecurityModule dTalkSecurityModule) {
        this.dTalkSecurityModule = dTalkSecurityModule;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {
        try {
            HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
            String code = resolveDTalkCode(httpServletRequest);
            if (StringUtils.hasText(code)) {
                if (this.dTalkSecurityModule.validateCode(code)) {
                    Authentication authentication = this.dTalkSecurityModule.getAuthentication(code);
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception e) {
            log.info("Security exception for user {} - {}", e.getLocalizedMessage());
            ((HttpServletResponse) servletResponse).setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        }
    }

    private String resolveDTalkUserId(HttpServletRequest request) {
        String dtUserId = request.getHeader(DTalkConfigurer.AUTHORIZATION_HEADER);
        if (StringUtils.hasText(dtUserId)) {
            return dtUserId;
        }
        return null;
    }

    private String resolveDTalkCode(HttpServletRequest request) {
        return request.getParameter(DTalkConfigurer.PARAMETER_CODE);
    }
}
