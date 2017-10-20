package com.alibaba.dingtalk.openapi.springbootdemo.integration.auth;

import com.alibaba.dingtalk.openapi.springbootdemo.config.DTalkProperties;
import com.dingtalk.open.client.api.model.corp.JsapiTicket;
import com.dingtalk.open.client.api.service.corp.CorpConnectionService;
import com.dingtalk.open.client.api.service.corp.JsapiService;
import com.dingtalk.open.client.common.ServiceException;
import com.alibaba.dingtalk.openapi.springbootdemo.integration.exception.OApiException;
import com.alibaba.dingtalk.openapi.springbootdemo.integration.exception.OApiResultException;
import com.alibaba.dingtalk.openapi.springbootdemo.repository.CorpAccessTokenRedisRepository;
import com.alibaba.dingtalk.openapi.springbootdemo.repository.CorpJsTicketRedisRepository;
import com.alibaba.dingtalk.openapi.springbootdemo.repository.model.CorpAccessToken;
import com.alibaba.dingtalk.openapi.springbootdemo.repository.model.CorpJsTicket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDateTime;
import java.util.Formatter;
import java.util.Objects;

@Slf4j
@Service
public class DingTalkAuthService {
    @Resource
    private CorpConnectionService corpConnectionService;
    @Resource
    private JsapiService jsapiService;
    private DTalkProperties dTalkProperties;
    private CorpAccessTokenRedisRepository corpAccessTokenRedisRepository;
    private CorpJsTicketRedisRepository corpJsTicketRedisRepository;

    @Autowired
    public DingTalkAuthService(DTalkProperties dTalkProperties,
                               CorpAccessTokenRedisRepository corpAccessTokenRedisRepository,
                               CorpJsTicketRedisRepository corpJsTicketRedisRepository) {
        this.dTalkProperties = dTalkProperties;
        this.corpAccessTokenRedisRepository = corpAccessTokenRedisRepository;
        this.corpJsTicketRedisRepository = corpJsTicketRedisRepository;
    }

    /*
     * 在此方法中，为了避免频繁获取access_token，
     * 在距离上一次获取access_token时间在两个小时之内的情况，
     * 将直接从持久化存储中读取access_token
     *
     * 因为access_token和jsapi_ticket的过期时间都是7200秒
     * 所以在获取access_token的同时也去获取了jsapi_ticket
     * 注：jsapi_ticket是在前端页面JSAPI做权限验证配置的时候需要使用的
     * 具体信息请查看开发者文档--权限验证配置
     */
    public String getAccessToken() throws OApiException {
        LocalDateTime now = LocalDateTime.now();
        String corpId = dTalkProperties.getCorpId();
        CorpAccessToken corpAccessToken = corpAccessTokenRedisRepository.findOne(corpId);
        String accToken = "";
        if (Objects.isNull(corpAccessToken) || now.isAfter(corpAccessToken.getBeginTime().plusSeconds(dTalkProperties.getCacheTime()))) {
            try {
                accToken = corpConnectionService.getCorpToken(corpId, dTalkProperties.getCorpSecret());
                CorpAccessToken token = new CorpAccessToken();
                token.setAccessToken(accToken);
                token.setBeginTime(now);
                token.setCortId(corpId);
                corpAccessTokenRedisRepository.save(token);
                if (accToken.length() > 0) {
                    createJsapiTicket(accToken);
                }
            } catch (ServiceException e) {
                if (log.isDebugEnabled()) {
                    e.printStackTrace();
                }
                log.error("DingTalkAuthService.getAccessToken ... ... exception {}", e.getLocalizedMessage());
            }

        } else {
            return corpAccessToken.getAccessToken();
        }

        return accToken;
    }

    // 正常的情况下，jsapi_ticket的有效期为7200秒，所以开发者需要在某个地方设计一个定时器，定期去更新jsapi_ticket
    public String getJsapiTicket(String accessToken) throws OApiException {
        LocalDateTime now = LocalDateTime.now();
        String corpId = dTalkProperties.getCorpId();
        CorpJsTicket corpJsTicket = corpJsTicketRedisRepository.findOne(corpId);
        String jsTicket = "";

        if (Objects.isNull(corpJsTicket) || now.isAfter(corpJsTicket.getBeginTime().plusSeconds(dTalkProperties.getCacheTime()))) {
            try {
                jsTicket = createJsapiTicket(accessToken);
            } catch (ServiceException e) {
                if (log.isDebugEnabled()) {
                    e.printStackTrace();
                }
                log.error("DingTalkAuthService.getJsapiTicket ... ... exception {}", e.getLocalizedMessage());
            }
            return jsTicket;
        } else {
            return corpJsTicket.getTicket();
        }
    }

    public String createJsapiTicket(String accessToken) throws ServiceException {

        JsapiTicket JsapiTicket = jsapiService.getJsapiTicket(accessToken, "jsapi");
        String jsTicket = JsapiTicket.getTicket();
        CorpJsTicket corpJsTicket = new CorpJsTicket();
        corpJsTicket.setCortId(dTalkProperties.getCorpId());
        corpJsTicket.setTicket(jsTicket);
        corpJsTicket.setBeginTime(LocalDateTime.now());
        corpJsTicketRedisRepository.save(corpJsTicket);
        return jsTicket;
    }


    public String sign(String ticket, String nonceStr, long timeStamp, String url) throws OApiException {
        String plain = "jsapi_ticket=" + ticket + "&noncestr=" + nonceStr + "&timestamp=" + String.valueOf(timeStamp)
                + "&url=" + url;
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            sha1.reset();
            sha1.update(plain.getBytes("UTF-8"));
            return bytesToHex(sha1.digest());
        } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
            throw new OApiResultException(e.getMessage());
        }
    }

    private String bytesToHex(byte[] hash) {
        Formatter formatter = new Formatter();
        for (byte b : hash) {
            formatter.format("%02x", b);
        }
        String result = formatter.toString();
        formatter.close();
        return result;
    }
}
