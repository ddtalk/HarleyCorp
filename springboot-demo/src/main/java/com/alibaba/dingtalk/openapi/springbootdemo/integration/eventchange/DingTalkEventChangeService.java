package com.alibaba.dingtalk.openapi.springbootdemo.integration.eventchange;

import com.dingtalk.open.client.api.model.corp.CallbackList;
import com.dingtalk.open.client.api.model.corp.callBackFailedResult;
import com.dingtalk.open.client.api.service.corp.CallBackService;
import com.dingtalk.open.client.common.ServiceException;
import com.alibaba.dingtalk.openapi.springbootdemo.integration.exception.OApiException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class DingTalkEventChangeService {
    @Resource
    private CallBackService callBackService;

    //注册事件回调接口
    public String registerEventChange(String accessToken, List<String> callBackTag, String token, String aesKey, String url) throws OApiException, ServiceException {
        return callBackService.registerCallBack(accessToken, callBackTag, token, aesKey, url);
    }

    //查询事件回调接口
    public CallbackList getEventChange(String accessToken) throws OApiException, ServiceException {
        return callBackService.getCallBack(accessToken);
    }

    //更新事件回调接口
    public String updateEventChange(String accessToken, List<String> callBackTag, String token, String aesKey, String url) throws OApiException, ServiceException {
        return callBackService.updateCallBack(accessToken, callBackTag, token, aesKey, url);
    }

    //删除事件回调接口
    public String deleteEventChange(String accessToken) throws OApiException, ServiceException {
        return callBackService.deleteCallBack(accessToken);
    }


    public callBackFailedResult getFailedResult(String accessToken) throws OApiException, ServiceException {
        return callBackService.getCallBackFailedResult(accessToken);
    }

}
