package com.alibaba.dingtalk.openapi.springbootdemo.config.security.model;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class UserAuthentication implements Authentication {

    private final UserContext user;
    private boolean authenticated = true;
    private AuthenticationType authenticationType;

    public UserAuthentication(UserContext authenticatedUser) {
        this.user = authenticatedUser;
    }

    public UserAuthentication(UserContext user, boolean authenticated, AuthenticationType authenticationType) {
        this.user = user;
        this.authenticated = authenticated;
        this.authenticationType = authenticationType;
    }

    public AuthenticationType getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(AuthenticationType authenticationType) {
        this.authenticationType = authenticationType;
    }

    @Override
    public String getName() {
        return user.getUsername();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getAuthorities();
    }

    @Override
    public Object getCredentials() {
        return user.getPassword();
    }

    @Override
    public UserContext getDetails() {
        return user;
    }

    @Override
    public Object getPrincipal() {
        return user.getUsername();
    }

    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @Override
    public void setAuthenticated(boolean authenticated) {
        this.authenticated = authenticated;
    }
}
