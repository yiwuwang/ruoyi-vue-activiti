package com.ruoyi.leave.service;

import java.util.List;

import com.ruoyi.activiti.domain.dto.HistoryDataDTO;
import com.ruoyi.leave.domain.WorkflowLeave;

/**
 * 请假Service接口
 * 
 * @author danny
 * @date 2020-10-28
 */
public interface IWorkflowLeaveService 
{
    /**
     * 查询请假
     * 
     * @param id 请假ID
     * @return 请假
     */
    public WorkflowLeave selectWorkflowLeaveById(String id);

    /**
     * 查询请假列表
     * 
     * @param workflowLeave 请假
     * @return 请假集合
     */
    public List<WorkflowLeave> selectWorkflowLeaveList(WorkflowLeave workflowLeave);

    /**
     * 查询请假列表
     *
     * @param workflowLeave 请假
     * @return 请假集合
     */
    public List<WorkflowLeave> selectWorkflowLeaveAndTaskNameList(WorkflowLeave workflowLeave);

    /**
     * 新增请假
     * 
     * @param workflowLeave 请假
     * @return 结果
     */
    public int insertWorkflowLeave(WorkflowLeave workflowLeave);

    /**
     * 修改请假
     * 
     * @param workflowLeave 请假
     * @return 结果
     */
    public int updateWorkflowLeave(WorkflowLeave workflowLeave);

    /**
     * 批量删除请假
     * 
     * @param ids 需要删除的请假ID
     * @return 结果
     */
    public int deleteWorkflowLeaveByIds(String[] ids);

    /**
     * 删除请假信息
     * 
     * @param id 请假ID
     * @return 结果
     */
    public int deleteWorkflowLeaveById(String id);


    public WorkflowLeave selectWorkflowLeaveByInstanceId(String instanceId);
}
