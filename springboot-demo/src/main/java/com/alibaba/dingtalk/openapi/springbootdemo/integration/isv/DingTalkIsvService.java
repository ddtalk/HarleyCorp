package com.alibaba.dingtalk.openapi.springbootdemo.integration.isv;

import com.dingtalk.open.client.api.model.isv.*;
import com.dingtalk.open.client.api.service.isv.IsvService;
import com.dingtalk.open.client.common.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class DingTalkIsvService {
    @Resource
    private IsvService isvService;

    public SuiteToken getSuiteToken(String suite_key, String suite_secret, String suite_ticket) throws ServiceException {
        return isvService.getSuiteToken(suite_key, suite_secret, suite_ticket);
    }

    public CorpAuthSuiteCode getPermanentCode(String tmp_auth_cod, String suiteAccessToken) throws ServiceException {
        return isvService.getPermanentCode(suiteAccessToken, tmp_auth_cod);
    }

    public CorpAuthToken getCorpToken(String auth_corpid, String permanent_code, String suiteAccessToken) throws ServiceException {
        return isvService.getCorpToken(suiteAccessToken, auth_corpid, permanent_code);
    }

    public CorpAuthInfo getAuthInfo(String suiteAccessToken, String suite_key, String auth_corpid, String permanent_code) throws ServiceException {
        return isvService.getAuthInfo(suiteAccessToken, suite_key, auth_corpid, permanent_code);
    }

    public CorpAgent getAgent(String suiteAccessToken, String suite_key, String auth_corpid, String permanent_code, String agentid) throws ServiceException {
        return isvService.getAgent(suiteAccessToken, suite_key, auth_corpid, agentid);
    }


    public void getActivateSuite(String suiteAccessToken, String suite_key, String auth_corpid, String permanent_code) throws ServiceException {
        isvService.activateSuite(suiteAccessToken, suite_key, auth_corpid);
    }
}
