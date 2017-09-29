package com.alibaba.dingtalk.openapi.springbootdemo.integration.media;

import com.dingtalk.open.client.api.model.corp.UploadResult;
import com.dingtalk.open.client.api.service.corp.MediaService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.File;

@Slf4j
@Service
public class DingTalkMediaService {
    @Resource
    private MediaService mediaService;

    public UploadResult upload(String accessToken, String type, File file) throws Exception {

        return mediaService.uploadMediaFile(accessToken, type, file);
    }
}
