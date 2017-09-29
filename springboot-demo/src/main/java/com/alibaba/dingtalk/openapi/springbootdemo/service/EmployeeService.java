package com.alibaba.dingtalk.openapi.springbootdemo.service;

import com.alibaba.dingtalk.openapi.springbootdemo.config.security.model.User;
import com.alibaba.dingtalk.openapi.springbootdemo.dal.dao.LufsDtalkXrefMapper;
import com.alibaba.dingtalk.openapi.springbootdemo.dal.dao.LufsUserMapper;
import com.alibaba.dingtalk.openapi.springbootdemo.dal.model.LufsDtalkXref;
import com.alibaba.dingtalk.openapi.springbootdemo.dal.model.LufsUser;
import com.alibaba.dingtalk.openapi.springbootdemo.model.enums.UserStatusEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@CacheConfig(cacheNames = "employee")
public class EmployeeService {
    private LufsDtalkXrefMapper dTalkXrefMapper;
    private LufsUserMapper userMapper;

    @Autowired
    public EmployeeService(LufsDtalkXrefMapper dTalkXrefMapper, LufsUserMapper userMapper) {
        this.dTalkXrefMapper = dTalkXrefMapper;
        this.userMapper = userMapper;
    }

    public Optional<User> findUserByLogin(String login) {
        return queryByEmpNo(login).map(lufsUser -> {
            User user = new User();
            user.setUsername(lufsUser.getEmpNo());
            user.setPassword(lufsUser.getPassword().getBytes(Charset.defaultCharset()));

            String userStatus = lufsUser.getUserStatus();
            if (Objects.isNull(userStatus) || !UserStatusEnum.ACTIVATED.getCode().equals(userStatus)) {
                user.setActivated(false);
            } else {
                user.setActivated(true);
            }
            return user;
        });
    }

    public Optional<User> authenticate(String principal, String credentials) {

        //TODO to be implemented...
        return Optional.empty();
    }

    @Cacheable(unless = "#result == null")
    public LufsDtalkXref queryDingtalkXref(String dingId) {
        LufsDtalkXref query = new LufsDtalkXref();
        query.setDtalkUserid(dingId);
        return dTalkXrefMapper.selectOne(query);
    }

    @Cacheable(unless = "#result == Optional.empty()")
    public Optional<LufsUser> queryByEmpNo(String empNo) {
        LufsUser query = new LufsUser();
        query.setEmpNo(empNo);
        return Optional.ofNullable(userMapper.selectOne(query));
    }
}
