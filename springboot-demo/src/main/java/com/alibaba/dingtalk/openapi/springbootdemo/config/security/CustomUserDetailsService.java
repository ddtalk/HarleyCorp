package com.alibaba.dingtalk.openapi.springbootdemo.config.security;

import com.alibaba.dingtalk.openapi.springbootdemo.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Authenticate a user from the database.
 */
@Slf4j
@Component("customUserDetailsService")
public class CustomUserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
    @Autowired
    private EmployeeService employeeService;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(final String login) {
        log.debug("CustomUserDetailsService.Authenticating {}", login);

        return employeeService.findUserByLogin(login)
                .map(user -> {
                    if (!user.isActivated()) {
                        throw new UserNotActivatedException("User " + login + " was not activated");
                    }
                    List<GrantedAuthority> grantedAuthorities = user.getAuthorities().stream()
                            .map(authority -> new SimpleGrantedAuthority(authority.getName()))
                            .collect(Collectors.toList());
                    return new org.springframework.security.core.userdetails.User(login,
                            new String(user.getPassword(), Charset.defaultCharset()),
                            grantedAuthorities);
                }).orElseThrow(() -> new UsernameNotFoundException("User " + login + " was not found in the " +
                        "database"));
    }
}
