package com.ruoyi.activiti.domain;

import com.ruoyi.activiti.domain.dto.ActWorkflowFormDataDTO;
import org.activiti.api.task.model.Task;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 动态单对象 act_workflow_formdata
 * 
 * @author danny
 * @date 2020-11-02
 */
public class ActWorkflowFormData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 唯一标识符 */
    private Long id;

    /** 实力ID */
    private String procInstId;

    /** 表单Key */
    private String formKey;


    /** 表单id */
    private String controlId;
    /** 表单名称 */
    private String controlName;

    /** 表单值 */
    private String controlValue;

    /** 任务节点名称 */
    private String taskNodeName;

    public ActWorkflowFormData() {
    }

    public ActWorkflowFormData(ActWorkflowFormDataDTO actWorkflowFormDataDTO, Task task) {
        this.procInstId = task.getProcessInstanceId();
        this.formKey = task.getFormKey();
        this.controlId = actWorkflowFormDataDTO.getControlId();
        this.controlName = actWorkflowFormDataDTO.getControlLable();
        if ("radio".equals(actWorkflowFormDataDTO.getControlType())) {
            int i = Integer.parseInt(actWorkflowFormDataDTO.getControlValue());
            this.controlValue =  actWorkflowFormDataDTO.getControlDefault().split("--__--")[i];
        }else {
            this.controlValue = actWorkflowFormDataDTO.getControlValue();
        }
        this.taskNodeName = task.getName();
    }

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setProcInstId(String procInstId) 
    {
        this.procInstId = procInstId;
    }

    public String getProcInstId() 
    {
        return procInstId;
    }
    public void setFormKey(String formKey) 
    {
        this.formKey = formKey;
    }

    public String getFormKey() 
    {
        return formKey;
    }
    public void setControlId(String controlId) 
    {
        this.controlId = controlId;
    }

    public String getControlId() 
    {
        return controlId;
    }

    public String getControlName() {
        return controlName;
    }

    public void setControlName(String controlName) {
        this.controlName = controlName;
    }

    public void setControlValue(String controlValue)
    {
        this.controlValue = controlValue;
    }

    public String getControlValue() 
    {
        return controlValue;
    }
    public void setTaskNodeName(String taskNodeName) 
    {
        this.taskNodeName = taskNodeName;
    }

    public String getTaskNodeName() 
    {
        return taskNodeName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("procInstId", getProcInstId())
            .append("formKey", getFormKey())
            .append("controlId", getControlId())
            .append("controlValue", getControlValue())
            .append("taskNodeName", getTaskNodeName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}
