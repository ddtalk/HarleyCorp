package com.alibaba.dingtalk.openapi.springbootdemo.config.security.jwt;

import com.alibaba.dingtalk.openapi.springbootdemo.config.OasProperties;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenProvider {

    private static final String AUTHORITIES_KEY = "auth";

    private String secretKey;

    private long tokenValidityInSeconds;

    private long tokenValidityInSecondsForRememberMe;

    @Autowired
    private OasProperties oasProperties;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @PostConstruct
    public void init() {
        this.secretKey = oasProperties.getSecurity().getAuthentication().getJwt().getSecret();

        this.tokenValidityInSeconds = 1000
                * oasProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSeconds();
        this.tokenValidityInSecondsForRememberMe = 1000
                * oasProperties.getSecurity().getAuthentication().getJwt().getTokenValidityInSecondsForRememberMe();
    }

    public String createToken(Authentication authentication, Boolean rememberMe) {
        log.info("TokenProvider.createToken ... Authentication {}, rememberMe {}", authentication, rememberMe);
        String authorities = authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = (new Date()).getTime();
        Date validity;
        long tokenExpire = this.tokenValidityInSeconds;
        if (rememberMe) {
            tokenExpire = this.tokenValidityInSecondsForRememberMe;
            // Time must be less than Integer.MAX_VALUE for setEx in Jedis.
            if (tokenExpire > Integer.MAX_VALUE) {
                tokenExpire = Integer.MAX_VALUE;
            }
        }
        validity = new Date(now + tokenExpire);
        String token = Jwts.builder().setSubject(authentication.getName()).claim(AUTHORITIES_KEY, authorities)
                .signWith(SignatureAlgorithm.HS512, secretKey).setExpiration(validity).compact();
        log.info("TokenProvider.createToken ... jwt token {}", token);
        if (!ObjectUtils.isEmpty(stringRedisTemplate.opsForValue().get(authentication.getName()))) {
            stringRedisTemplate.delete(authentication.getName());
        }

        stringRedisTemplate.opsForValue().set(authentication.getName(), token, tokenExpire, TimeUnit.SECONDS);
        return token;
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
        if (!stringRedisTemplate.hasKey(claims.getSubject())) {
            return null;
        }
        Collection<? extends GrantedAuthority> authorities = Arrays
                .asList(claims.get(AUTHORITIES_KEY).toString().split(",")).stream()
                .map(SimpleGrantedAuthority::new).collect(Collectors.toList());

        User principal = new User(claims.getSubject(), "", authorities);

        return new UsernamePasswordAuthenticationToken(principal, "", authorities);
    }

    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (SignatureException e) {
            log.info("Invalid JWT signature: " + e.getMessage());
            return false;
        }
    }
}
