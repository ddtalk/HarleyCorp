package com.alibaba.dingtalk.openapi.springbootdemo.config.security.model;

public enum UserRole {
    USER, ADMIN, ANONYMOUS;

    public UserAuthority asAuthorityFor(final UserContext user) {
        final UserAuthority authority = new UserAuthority();
        authority.setAuthority("ROLE_" + toString());
        authority.setUser(user);
        return authority;
    }

    public static UserRole valueOf(final UserAuthority authority) {
        switch (authority.getAuthority()) {
            case AuthoritiesConstants.USER:
                return USER;
            case AuthoritiesConstants.ADMIN:
                return ADMIN;
            case AuthoritiesConstants.ANONYMOUS:
                return ANONYMOUS;
        }
        throw new IllegalArgumentException("No role defined for authority: " + authority.getAuthority());
    }
}
