package com.alibaba.dingtalk.openapi.springbootdemo.integration.message;

import com.dingtalk.open.client.api.model.corp.MessageSendResult;
import com.dingtalk.open.client.api.service.corp.MessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Slf4j
@Service
public class DingTalkMessageService {
    @Resource
    private MessageService messageService;

    public Receipt send(String accessToken, LightAppMessageDelivery delivery)
            throws Exception {
        MessageSendResult reulst = messageService.sendToCorpConversation(accessToken, delivery.touser,
                delivery.toparty, delivery.agentid, delivery.msgType, delivery.message);
        Receipt receipt = new Receipt();
        receipt.invaliduser = reulst.getInvaliduser();
        receipt.invalidparty = reulst.getInvalidparty();
        return receipt;
    }


    public String send(String accessToken, ConversationMessageDelivery delivery)
            throws Exception {
        return messageService.sendToNormalConversation(accessToken, delivery.sender,
                delivery.cid, delivery.msgType, delivery.message);
    }
}
