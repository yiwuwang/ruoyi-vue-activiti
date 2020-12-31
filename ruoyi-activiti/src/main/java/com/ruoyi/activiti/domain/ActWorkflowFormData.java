package com.ruoyi.activiti.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 动态单对象 act_workflow_formdata
 * 
 * @author danny
 * @date 2020-12-30
 */
public class ActWorkflowFormData extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 唯一标识符 */
    private Long id;

    /** 事务key */
    @Excel(name = "事务key")
    private String businessKey;

    /** 表单Key */
    @Excel(name = "表单Key")
    private String formKey;

    /** 表单值 */
    @Excel(name = "表单值")
    private String formData;

    /** 表单属性定义 */
    @Excel(name = "表单属性定义")
    private String formDesc;

    /** 任务节点名称 */
    @Excel(name = "任务节点名称")
    private String taskNodeName;

    /** 创建者姓名 */
    private String createName;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setBusinessKey(String businessKey) 
    {
        this.businessKey = businessKey;
    }

    public String getBusinessKey() 
    {
        return businessKey;
    }
    public void setFormKey(String formKey) 
    {
        this.formKey = formKey;
    }

    public String getFormKey() 
    {
        return formKey;
    }
    public void setFormData(String formData) 
    {
        this.formData = formData;
    }

    public String getFormData() 
    {
        return formData;
    }
    public void setFormDesc(String formDesc) 
    {
        this.formDesc = formDesc;
    }

    public String getFormDesc() 
    {
        return formDesc;
    }
    public void setTaskNodeName(String taskNodeName) 
    {
        this.taskNodeName = taskNodeName;
    }

    public String getTaskNodeName() 
    {
        return taskNodeName;
    }
    public void setCreateName(String createName) 
    {
        this.createName = createName;
    }

    public String getCreateName() 
    {
        return createName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("businessKey", getBusinessKey())
            .append("formKey", getFormKey())
            .append("formData", getFormData())
            .append("formDesc", getFormDesc())
            .append("taskNodeName", getTaskNodeName())
            .append("createName", getCreateName())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .toString();
    }
}
