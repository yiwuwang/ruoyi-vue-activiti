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

    /** 事务Id */
    private String businessKey;

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

    private String createName;

    public ActWorkflowFormData() {
    }

    public ActWorkflowFormData(String businessKey,ActWorkflowFormDataDTO actWorkflowFormDataDTO, Task task) {
        this.businessKey = businessKey;
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

    public String getBusinessKey() {
        return businessKey;
    }

    public void setBusinessKey(String businessKey) {
        this.businessKey = businessKey;
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

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("procInstId", getBusinessKey())
            .append("formKey", getFormKey())
            .append("controlId", getControlId())
            .append("controlValue", getControlValue())
            .append("taskNodeName", getTaskNodeName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}
