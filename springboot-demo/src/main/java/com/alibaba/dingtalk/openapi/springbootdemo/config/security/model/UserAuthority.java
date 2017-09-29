package com.alibaba.dingtalk.openapi.springbootdemo.config.security.model;

import org.springframework.security.core.GrantedAuthority;

public class UserAuthority implements GrantedAuthority {

    private UserContext user;

    private String authority;

    public UserContext getUser() {
        return user;
    }

    public void setUser(UserContext user) {
        this.user = user;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof UserAuthority))
            return false;

        UserAuthority ua = (UserAuthority) obj;
        return ua.getAuthority() == this.getAuthority() || ua.getAuthority().equals(this.getAuthority());
    }

    @Override
    public int hashCode() {
        return getAuthority() == null ? 0 : getAuthority().hashCode();
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + getAuthority();
    }
}
