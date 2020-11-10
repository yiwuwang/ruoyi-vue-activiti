package com.ruoyi.leave.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.ruoyi.activiti.domain.dto.HistoryFormDataDTO;
import com.ruoyi.activiti.domain.dto.HistoryDataDTO;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.UUID;
import com.ruoyi.system.service.ISysUserService;
import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.ProcessPayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.leave.mapper.WorkflowLeaveMapper;
import com.ruoyi.leave.domain.WorkflowLeave;
import com.ruoyi.leave.service.IWorkflowLeaveService;

/**
 * 请假Service业务层处理
 *
 * @author danny
 * @date 2020-10-28
 */
@Service
public class WorkflowLeaveServiceImpl implements IWorkflowLeaveService {

    @Autowired
    private WorkflowLeaveMapper workflowLeaveMapper;

    @Autowired
    private ProcessRuntime processRuntime;
    @Autowired
    private ISysUserService sysUserService;


    /**
     * 查询请假
     *
     * @param id 请假ID
     * @return 请假
     */
    @Override
    public WorkflowLeave selectWorkflowLeaveById(String id) {
        return workflowLeaveMapper.selectWorkflowLeaveById(id);
    }

    /**
     * 查询请假列表
     *
     * @param workflowLeave 请假
     * @return 请假
     */
    @Override
    public List<WorkflowLeave> selectWorkflowLeaveList(WorkflowLeave workflowLeave) {
        return workflowLeaveMapper.selectWorkflowLeaveList(workflowLeave);
    }

    /**
     * 新增请假
     *
     * @param workflowLeave 请假
     * @return 结果
     */
    @Override
    public int insertWorkflowLeave(WorkflowLeave workflowLeave) {

        String id = UUID.randomUUID().toString();
        workflowLeave.setId(id);
        workflowLeave.setCreateTime(DateUtils.getNowDate());
        Long deptId = SecurityUtils.getLoginUser().getUser().getDeptId();
        List<String> se = sysUserService.selectUserNameByPostCodeAndDeptId("se", deptId);
        String join = StringUtils.join(se, ",");
        ProcessInstance processInstance = processRuntime.start(ProcessPayloadBuilder
                .start()
                .withProcessDefinitionKey("leave")
                .withName(workflowLeave.getTitle())
                .withBusinessKey(id)
                .withVariable("deptLeader",join)
                .build());
        workflowLeave.setInstanceId(processInstance.getId());
        workflowLeave.setState("0");
        workflowLeave.setCreateBy(SecurityUtils.getUsername());
        workflowLeave.setCreateTime(DateUtils.getNowDate());
        return workflowLeaveMapper.insertWorkflowLeave(workflowLeave);
    }

    /**
     * 修改请假
     *
     * @param workflowLeave 请假
     * @return 结果
     */
    @Override
    public int updateWorkflowLeave(WorkflowLeave workflowLeave) {
        workflowLeave.setUpdateTime(DateUtils.getNowDate());
        return workflowLeaveMapper.updateWorkflowLeave(workflowLeave);
    }

    /**
     * 批量删除请假
     *
     * @param ids 需要删除的请假ID
     * @return 结果
     */
    @Override
    public int deleteWorkflowLeaveByIds(String[] ids) {
        return workflowLeaveMapper.deleteWorkflowLeaveByIds(ids);
    }

    /**
     * 删除请假信息
     *
     * @param id 请假ID
     * @return 结果
     */
    @Override
    public int deleteWorkflowLeaveById(String id) {
        return workflowLeaveMapper.deleteWorkflowLeaveById(id);
    }

    @Override
    public WorkflowLeave selectWorkflowLeaveByInstanceId(String instanceId) {

        return workflowLeaveMapper.selectWorkflowLeaveByInstanceId(instanceId);
    }
}
