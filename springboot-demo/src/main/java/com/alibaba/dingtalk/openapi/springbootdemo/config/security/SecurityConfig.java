package com.alibaba.dingtalk.openapi.springbootdemo.config.security;

import com.alibaba.dingtalk.openapi.springbootdemo.config.security.dingtalk.DTalkSecurityModule;
import com.alibaba.dingtalk.openapi.springbootdemo.config.security.jwt.JWTConfigurer;
import com.alibaba.dingtalk.openapi.springbootdemo.config.security.jwt.TokenProvider;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private DTalkSecurityModule dTalkSecurityModule;
    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private Http401UnauthorizedEntryPoint authenticationEntryPoint;
    @Autowired
    @Qualifier("customUserDetailsService")
    private UserDetailsService customUserDetailsService;
    @Autowired
    @Qualifier("customAuthenticationProvider")
    private AuthenticationProvider customAuthenticationProvider;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) {
        try {
            auth.authenticationProvider(customAuthenticationProvider).userDetailsService(customUserDetailsService);
            // .passwordEncoder(passwordEncoder());
        } catch (Exception e) {
            throw new BeanInitializationException("Security configuration failed", e);
        }
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(HttpMethod.OPTIONS, "/**").antMatchers("/i18n/**")
                .antMatchers("/webjars/**").antMatchers("/swagger-ui.html");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().authenticationEntryPoint(authenticationEntryPoint)
                .and().csrf().disable().headers().frameOptions().disable()
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().authorizeRequests()
                .antMatchers("/css/**", "/index", "/v2/api-docs/**", "/api/v1/oa/dt/app/init/**", "/api/v1/oa/dt/user/authenticate/**").permitAll()
                .antMatchers("/api/v1/**").hasRole("USER")//.permitAll()
                .and()
                .formLogin()
                .loginPage("/login").failureUrl("/login-error")
                .and().apply(securityConfigurerAdapter());
    }

    private JWTConfigurer securityConfigurerAdapter() {
        return new JWTConfigurer(tokenProvider);
    }

/*    private DTalkConfigurer securityConfigurerAdapter() {
        return new DTalkConfigurer(dTalkSecurityModule);
    }*/
}
