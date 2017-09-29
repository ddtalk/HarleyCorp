package com.alibaba.dingtalk.openapi.springbootdemo.config.security.model;

import org.springframework.security.core.userdetails.UserDetails;

import java.nio.charset.Charset;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;


public class UserContext implements UserDetails {

    private User user;

    public UserContext(User user) {
        this.user = user;

        if (user != null) {
            this.username = user.getUsername();
            byte[] password = user.getPassword();
            if (!Objects.isNull(password)) {
                this.password = new String(password, Charset.defaultCharset());
            }
        }
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserContext(String username) {
        this.username = username;
    }

    public UserContext(String username, Date expires) {
        this.username = username;
        this.expires = expires.getTime();
    }

    public UserContext(final String username,
                       final String password) {
        this.setPassword(password);
        this.setUsername(username);

    }

    private String username;

    private String password;

    private long expires;

    private boolean accountExpired;

    private boolean accountLocked;

    private boolean credentialsExpired;

    private boolean accountEnabled;

    private Set<UserAuthority> authorities;

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Set<UserAuthority> getAuthorities() {
        return authorities;
    }

    // Use Roles as external API
    public Set<UserRole> getRoles() {
        return authorities.stream().map(UserRole::valueOf).collect(Collectors.toSet());
    }

    public void setRoles(Set<UserRole> roles) {
        roles.forEach(this::grantRole);
    }

    public void grantRole(UserRole role) {
        if (authorities == null) {
            authorities = new HashSet<>();
        }
        authorities.add(role.asAuthorityFor(this));
    }

    public void revokeRole(UserRole role) {
        if (authorities != null) {
            authorities.remove(role.asAuthorityFor(this));
        }
    }

    public boolean hasRole(UserRole role) {
        return authorities.contains(role.asAuthorityFor(this));
    }

    @Override
    public boolean isAccountNonExpired() {
        return !accountExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !accountLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return !credentialsExpired;
    }

    @Override
    public boolean isEnabled() {
        return !accountEnabled;
    }

    public long getExpires() {
        return expires;
    }

    public void setExpires(long expires) {
        this.expires = expires;
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + getUsername();
    }
}
