package com.alibaba.dingtalk.openapi.springbootdemo.api;


import com.alibaba.dingtalk.openapi.springbootdemo.config.security.jwt.JWTConfigurer;
import com.alibaba.dingtalk.openapi.springbootdemo.config.security.jwt.TokenProvider;
import com.alibaba.dingtalk.openapi.springbootdemo.config.security.model.AuthenticationType;
import com.alibaba.dingtalk.openapi.springbootdemo.config.security.model.User;
import com.alibaba.dingtalk.openapi.springbootdemo.config.security.model.UserAuthentication;
import com.alibaba.dingtalk.openapi.springbootdemo.config.security.model.UserContext;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Collections;

@Api
@Slf4j
@RequestMapping("/api/v1/oa/dt/user")
@RestController
public class DTalkUserRestController {

    @Autowired
    private TokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostMapping(value = "authenticate/{code}/{rememberme}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<?> authenticate(@PathVariable("code") String code, @PathVariable(value = "rememberme", required = false) Boolean rememberme, HttpServletResponse response) {
        log.info("DTalkUserRestController.authorize starting ... ... code", code);
        User user = new User();
        user.setUsername(code);
        UserContext userContext = new UserContext(user);
        UserAuthentication userAuthentication = new UserAuthentication(userContext, false, AuthenticationType.DING_TALK_CODE);

        try {
            Authentication authentication = this.authenticationManager.authenticate(userAuthentication);

            // null process for authentication
            UserAuthentication userAuthentication1 = (UserAuthentication) authentication;
            log.debug("DTalkUserRestController.authorize authenticated ... ... UserAuthentication", userAuthentication1);
            UserContext details = userAuthentication1.getDetails();
            User user1 = details.getUser();

            SecurityContextHolder.getContext().setAuthentication(authentication);
            boolean rememberMe = (rememberme == null) ? false : rememberme;
            String jwt = tokenProvider.createToken(authentication, rememberMe);

            response.addHeader(JWTConfigurer.AUTHORIZATION_HEADER, "Bearer " + jwt);

            return ResponseEntity.ok(user1);
        } catch (AuthenticationException ae) {
            if (log.isDebugEnabled()) {
                ae.printStackTrace();
            }
            return new ResponseEntity<>(
                    Collections.singletonMap("AuthenticationException", ae.getLocalizedMessage()),
                    HttpStatus.UNAUTHORIZED);
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                e.printStackTrace();
            }
            return new ResponseEntity<>(Collections.singletonMap("AuthenticateException", e.getLocalizedMessage()),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/logout/{userId}")
    public ResponseEntity<?> logout(@Valid @PathVariable("userId") String userId) {
        try {
            stringRedisTemplate.delete(userId);
            return ResponseEntity.ok("logout successfully!");
        } catch (Exception exception) {
            return new ResponseEntity<>(Collections.singletonMap("LogoutException", exception.getLocalizedMessage()),
                    HttpStatus.BAD_GATEWAY);
        }
    }
}
