package com.alibaba.dingtalk.openapi.springbootdemo.integration.department;

import com.dingtalk.open.client.api.model.corp.Department;
import com.dingtalk.open.client.api.model.corp.DepartmentDetail;
import com.dingtalk.open.client.api.service.corp.CorpDepartmentService;
import com.dingtalk.open.client.common.SdkInitException;
import com.dingtalk.open.client.common.ServiceException;
import com.dingtalk.open.client.common.ServiceNotExistException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class DingTalkDeptService {
    @Resource
    private CorpDepartmentService corpDepartmentService;

    public void setCorpDepartmentService(CorpDepartmentService corpDepartmentService) {
        this.corpDepartmentService = corpDepartmentService;
    }

    public String createDepartment(String accessToken, String name,
                                   String parentId, String order, boolean createDeptGroup) throws Exception {

        return corpDepartmentService.deptCreate(accessToken, name, parentId, order, createDeptGroup);
    }


    public List<Department> listDepartments(String accessToken, String parentDeptId)
            throws ServiceNotExistException, SdkInitException, ServiceException {
        return corpDepartmentService.getDeptList(accessToken, parentDeptId);
    }


    public void deleteDepartment(String accessToken, Long id) throws Exception {
        corpDepartmentService.deptDelete(accessToken, id);
    }


    public void updateDepartment(String accessToken, long id, String name,
                                 String parentId, String order, Boolean createDeptGroup,
                                 boolean autoAddUser, String deptManagerUseridList, boolean deptHiding, String deptPerimits,
                                 String userPerimits, Boolean outerDept, String outerPermitDepts,
                                 String outerPermitUsers, String orgDeptOwner) throws Exception {
        corpDepartmentService.deptUpdate(accessToken, id, name, parentId, order, createDeptGroup,
                autoAddUser, deptManagerUseridList, deptHiding, deptPerimits, userPerimits,
                outerDept, outerPermitDepts, outerPermitUsers, orgDeptOwner);
    }

    //获取部门详情 liuchi
    public DepartmentDetail getDeptDetail(String accessToken, String id) throws ServiceException {
        return corpDepartmentService.getDeptDetail(accessToken, id);
    }
}
