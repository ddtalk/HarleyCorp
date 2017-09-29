package com.alibaba.dingtalk.openapi.springbootdemo.api;

import com.alibaba.dingtalk.openapi.springbootdemo.api.errors.CustomParameterizedException;
import com.alibaba.dingtalk.openapi.springbootdemo.config.DTalkProperties;
import com.alibaba.dingtalk.openapi.springbootdemo.integration.auth.DingTalkAuthService;
import com.alibaba.dingtalk.openapi.springbootdemo.integration.exception.OApiException;
import com.alibaba.dingtalk.openapi.springbootdemo.model.api.AppInitInfoReq;
import com.alibaba.dingtalk.openapi.springbootdemo.model.dto.AppInitInfo;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.UUID;


@Api(hidden = true)
@Slf4j
@RestController
@RequestMapping("/api/v1/oa/dt/app")
public class AppInitRestController {
    @Autowired
    private DTalkProperties dTalkProperties;
    @Autowired
    private DingTalkAuthService dingTalkAuthService;

    @PostMapping(value = "init/agent-id/{agentId}", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<AppInitInfo> init(@RequestBody AppInitInfoReq req, @PathVariable("agentId") String agentId) throws UnsupportedEncodingException {
        String validAgentId = dTalkProperties.getAgentId();
        try {
            if (!validAgentId.equals(agentId)) {
                throw new CustomParameterizedException("Invalid agent id");
            }
            String url = req.getUrl();
            String signedUrl = URLDecoder.decode(url, "UTF8");

            String nonceStr = UUID.randomUUID().toString().replace("-", "");
            long timestamp = System.currentTimeMillis();
            String jsTicket = dingTalkAuthService.getJsapiTicket(dingTalkAuthService.getAccessToken());
            String signature = dingTalkAuthService.sign(jsTicket, nonceStr, timestamp, signedUrl);

            AppInitInfo initInfo = new AppInitInfo();
            initInfo.setSignature(signature);
            initInfo.setAgentId(agentId);
            initInfo.setCorpId(dTalkProperties.getCorpId());
            initInfo.setNonceStr(nonceStr);
            initInfo.setTimestamp(String.valueOf(timestamp));

            return ResponseEntity.ok(initInfo);

        } catch (OApiException e) {
            if (log.isDebugEnabled()) {
                e.printStackTrace();
            }
            throw new RuntimeException(e);
        }
    }
}
