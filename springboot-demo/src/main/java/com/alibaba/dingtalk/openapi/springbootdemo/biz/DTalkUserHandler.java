package com.alibaba.dingtalk.openapi.springbootdemo.biz;

import com.alibaba.dingtalk.openapi.springbootdemo.config.security.model.User;
import com.alibaba.dingtalk.openapi.springbootdemo.dal.model.LufsDtalkXref;
import com.alibaba.dingtalk.openapi.springbootdemo.integration.auth.DingTalkAuthService;
import com.alibaba.dingtalk.openapi.springbootdemo.integration.user.DTalkUserService;
import com.alibaba.dingtalk.openapi.springbootdemo.service.EmployeeService;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.nio.charset.Charset;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Component
public class DTalkUserHandler {
    @Autowired
    private DTalkUserService dTalkUserService;
    @Autowired
    private EmployeeService employeeService;
    @Resource
    private DingTalkAuthService dingTalkAuthService;

    public Optional<User> authenticate(String principal) {
        String accessToken;
        try {
            accessToken = dingTalkAuthService.getAccessToken();
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                e.printStackTrace();
            }
            log.error("DTalkUserHandler.authenticate ... ... exception occurs getAccessToken", e.getLocalizedMessage());
            return Optional.empty();
        }

        String dingId = null;
        try {
            CorpUserDetail userInfo = dTalkUserService.getUserInfo(accessToken, principal);
            if (!Objects.isNull(userInfo)) {
                dingId = userInfo.getUserid();
            }
        } catch (Exception e) {
            if (log.isDebugEnabled()) {
                e.printStackTrace();
            }
            log.error("DTalkUserHandler.authenticate ... ... exception occurs getUserInfo", e.getLocalizedMessage());
        }

        if (Objects.isNull(dingId)) {
            return Optional.empty();
        }

        LufsDtalkXref dingtalkXref = employeeService.queryDingtalkXref(dingId);
        if (Objects.isNull(dingtalkXref)) {
            return Optional.empty();
        }

        String empNo = dingtalkXref.getEmpNo();
        if (Objects.isNull(empNo)) {
            return Optional.empty();
        }

        final String dTalkId = dingId;
        return employeeService.queryByEmpNo(empNo)
                .map(e -> {
                    User user = new User();
                    user.setUsername(e.getEmpNo());
                    user.setPassword(e.getPassword().getBytes(Charset.defaultCharset()));
                    user.setEmpNo(e.getEmpNo());
                    user.setActivated(true);
                    user.setDTalkUserId(dTalkId);
                    return user;
                });
    }
}
