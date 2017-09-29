package com.alibaba.dingtalk.openapi.springbootdemo.integration.user;

import com.alibaba.fastjson.JSONObject;
import com.dingtalk.open.client.api.model.corp.CorpUserDetail;
import com.dingtalk.open.client.api.model.corp.CorpUserDetailList;
import com.dingtalk.open.client.api.model.corp.CorpUserList;
import com.dingtalk.open.client.api.model.corp.SsoUserInfo;
import com.dingtalk.open.client.api.service.corp.CorpUserService;
import com.dingtalk.open.client.api.service.corp.SsoService;
import com.dingtalk.open.client.common.ServiceException;
import com.alibaba.dingtalk.openapi.springbootdemo.integration.exception.OApiException;
import com.alibaba.dingtalk.openapi.springbootdemo.integration.utils.FileUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service
public class DingTalkUserService {
    @Resource
    private CorpUserService corpUserService;
    @Resource
    private SsoService ssoService;

    //创建成员
    public void createUser(String accessToken, CorpUserDetail userDetail) throws Exception {
        JSONObject js = (JSONObject) JSONObject.parse(userDetail.getOrderInDepts());
        Map<Long, Long> orderInDepts = FileUtils.toHashMap(js);

        String userid = corpUserService.createCorpUser(accessToken, userDetail.getUserid(), userDetail.getName(), orderInDepts,
                userDetail.getDepartment(), userDetail.getPosition(), userDetail.getMobile(), userDetail.getTel(), userDetail.getWorkPlace(),
                userDetail.getRemark(), userDetail.getEmail(), userDetail.getJobnumber(),
                userDetail.getIsHide(), userDetail.getSenior(), userDetail.getExtattr());
    }


    //更新成员
    public void updateUser(String accessToken, CorpUserDetail userDetail) throws Exception {
        JSONObject js = (JSONObject) JSONObject.parse(userDetail.getOrderInDepts());
        Map<Long, Long> orderInDepts = FileUtils.toHashMap(js);


        String userid = corpUserService.updateCorpUser(accessToken, userDetail.getUserid(), userDetail.getName(), orderInDepts,
                userDetail.getDepartment(), userDetail.getPosition(), userDetail.getMobile(), userDetail.getTel(), userDetail.getWorkPlace(),
                userDetail.getRemark(), userDetail.getEmail(), userDetail.getJobnumber(),
                userDetail.getIsHide(), userDetail.getSenior(), userDetail.getExtattr());
    }


    //删除成员
    public void deleteUser(String accessToken, String userid) throws Exception {
        CorpUserDetail detail = corpUserService.deleteCorpUser(accessToken, userid);
    }


    //获取成员
    public CorpUserDetail getUser(String accessToken, String userid) throws Exception {
        return corpUserService.getCorpUser(accessToken, userid);
    }

    //批量删除成员
    public void batchDeleteUser(String accessToken, List<String> useridlist)
            throws Exception {
        corpUserService.batchdeleteCorpUserListByUserids(accessToken, useridlist);
    }


    //获取部门成员
    public CorpUserList getDepartmentUser(
            String accessToken,
            long departmentId,
            Long offset,
            Integer size,
            String order)
            throws Exception {

        return corpUserService.getCorpUserSimpleList(accessToken, departmentId,
                offset, size, order);
    }


    //获取部门成员（详情）
    public CorpUserDetailList getUserDetails(
            String accessToken,
            long departmentId,
            Long offset,
            Integer size,
            String order)
            throws Exception {

        return corpUserService.getCorpUserList(accessToken, departmentId,
                offset, size, order);
    }

    public CorpUserDetail getUserInfo(String accessToken, String code) throws Exception {
        return corpUserService.getUserinfo(accessToken, code);
    }

    public SsoUserInfo getAgentUserInfo(String ssoToken, String code) throws OApiException, ServiceException {
        return ssoService.getSSOUserinfo(ssoToken, code);
    }

}
