package com.alibaba.dingtalk.openapi.springbootdemo.service;

import com.alibaba.dingtalk.openapi.springbootdemo.config.security.model.User;
import com.alibaba.dingtalk.openapi.springbootdemo.dal.dao.LufsDtalkXrefMapper;
import com.alibaba.dingtalk.openapi.springbootdemo.dal.dao.LufsUserMapper;
import com.alibaba.dingtalk.openapi.springbootdemo.dal.model.LufsDtalkXref;
import com.alibaba.dingtalk.openapi.springbootdemo.dal.model.LufsUser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class EmployeeService {
    private LufsDtalkXrefMapper dTalkXrefMapper;
    private LufsUserMapper userMapper;

    @Autowired
    public EmployeeService(LufsDtalkXrefMapper dTalkXrefMapper, LufsUserMapper userMapper) {
        this.dTalkXrefMapper = dTalkXrefMapper;
        this.userMapper = userMapper;
    }

    public Optional<User> findUserByLogin(String login) {
        return queryByEmpNo(login).map(x->{
            User user = new User();

            return user;
        });
    }

    public Optional<User> authenticate(String principal, String credentials) {
        return null;
    }

    public LufsDtalkXref queryDingtalkXref(String dingId) {
        LufsDtalkXref query = new LufsDtalkXref();
        query.setDtalkUserid(dingId);
        return dTalkXrefMapper.selectOne(query);
    }

    public Optional<LufsUser> queryByEmpNo(String empNo) {
        LufsUser query = new LufsUser();
        query.setEmpNo(empNo);
        return Optional.ofNullable(userMapper.selectOne(query));
    }
}
