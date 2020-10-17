package com.ruoyi.activiti.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ActivitiBaseEntity extends BaseEntity {

    /** 流程实例ID */
    @Excel(name = "流程实例ID")
    private String instanceId;
    /** 申请人姓名 */
    private String applyUserName;
    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 原因 */
    @Excel(name = "原因")
    private String reason;

    /** 任务ID */
    private String taskId;

    /** 任务名称 */
    private String taskName;

    /** 办理时间 */
    private Date doneTime;

    /** 创建人 */
    private String createUserName;


    /** 流程实例状态 1 激活 2 挂起 */
    private String suspendState;
    private Map<String, Object> processParams;


    public String getApplyUserName() {
        return applyUserName;
    }

    public void setApplyUserName(String applyUserName) {
        this.applyUserName = applyUserName;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public Date getDoneTime() {
        return doneTime;
    }

    public void setDoneTime(Date doneTime) {
        this.doneTime = doneTime;
    }

    public String getCreateUserName() {
        return createUserName;
    }

    public void setCreateUserName(String createUserName) {
        this.createUserName = createUserName;
    }

    public String getSuspendState() {
        return suspendState;
    }

    public void setSuspendState(String suspendState) {
        this.suspendState = suspendState;
    }

    public void setInstanceId(String instanceId)
    {
        this.instanceId = instanceId;
    }

    public String getInstanceId()
    {
        return instanceId;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }
    public void setReason(String reason)
    {
        this.reason = reason;
    }

    public String getReason()
    {
        return reason;
    }

    public Map<String, Object> getProcessParams() {
        if (processParams == null)
        {
            processParams = new HashMap<>();
        }
        return processParams;
    }

    public void setProcessParams(Map<String, Object> processParams) {
        this.processParams = processParams;
    }
}
