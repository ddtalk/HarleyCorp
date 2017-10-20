package com.alibaba.dingtalk.openapi.springbootdemo.config.security;

import com.alibaba.dingtalk.openapi.springbootdemo.biz.DTalkUserHandler;
import com.alibaba.dingtalk.openapi.springbootdemo.config.security.model.AuthenticationType;
import com.alibaba.dingtalk.openapi.springbootdemo.config.security.model.UserAuthentication;
import com.alibaba.dingtalk.openapi.springbootdemo.config.security.model.UserContext;
import com.alibaba.dingtalk.openapi.springbootdemo.config.security.model.UserRole;
import com.alibaba.dingtalk.openapi.springbootdemo.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
@Slf4j
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private DTalkUserHandler dTalkUserHandler;
    @Autowired
    private EmployeeService employeeService;


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        log.debug("CustomAuthenticationProvider.authenticate ... ... Authentication {}", authentication);

        UserAuthentication userAuthentication = (UserAuthentication) authentication;
        AuthenticationType authenticationType = userAuthentication.getAuthenticationType();

        switch (authenticationType) {
            case OA_LOGIN:
                return authenticateByOaLogin(userAuthentication);
            case DING_TALK_CODE:
                return authenticateByDTalkCode(userAuthentication);
            default:
                return authenticateByOaLogin(userAuthentication);
        }
    }

    private UserAuthentication authenticateByOaLogin(UserAuthentication userAuthentication) {
        UserContext userContext = userAuthentication.getDetails();
        return employeeService.authenticate((String) userAuthentication.getPrincipal(), (String) userAuthentication.getCredentials())
                .map(user -> {
                    userContext.setUser(user);
                    Set<UserRole> userRoles = new HashSet<>();
                    UserRole userRole = UserRole.USER;
                    userRoles.add(userRole);
                    userContext.setRoles(userRoles);
                    userAuthentication.setAuthenticated(true);
                    return userAuthentication;
                }).orElseGet(() -> {
                    throw new BadCredentialsException("Login failed!");
                });
    }

    private UserAuthentication authenticateByDTalkCode(UserAuthentication userAuthentication) {
        UserContext userContext = userAuthentication.getDetails();
        return dTalkUserHandler.authenticate((String) userAuthentication.getPrincipal()).map(user -> {
            userContext.setUser(user);
            Set<UserRole> userRoles = new HashSet<>();
            UserRole userRole = UserRole.USER;
            userRoles.add(userRole);
            userContext.setRoles(userRoles);
            userAuthentication.setAuthenticated(true);
            return userAuthentication;
        }).orElseGet(() -> {
            throw new BadCredentialsException("Login failed!");
        });
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UserAuthentication.class);
    }
}
