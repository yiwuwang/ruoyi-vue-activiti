package com.ruoyi.activiti.domain.dto;


import com.ruoyi.common.core.domain.BaseEntity;


/**
 * 动态单对象 act_workflow_formdata
 * 
 * @author danny
 * @date 2020-11-01
 */
public class ActWorkflowFormDataDTO extends BaseEntity
{
    private static final long serialVersionUID = 1L;


    /** 表单id */
    private String controlId;
    private String controlType;



    /** 表单名称 */
    private String controlLable;

    private String controlIsParam;

    /** 表单值 */
    private String controlValue;
    private String controlDefault;



    public void setControlId(String controlId) 
    {
        this.controlId = controlId;
    }

    public String getControlId() 
    {
        return controlId;
    }
    public void setControlValue(String controlValue) 
    {
        this.controlValue = controlValue;
    }

    public String getControlValue() 
    {
        return controlValue;
    }


    public String getControlIsParam() {
        return controlIsParam;
    }

    public void setControlIsParam(String controlIsParam) {
        this.controlIsParam = controlIsParam;
    }

    public String getControlLable() {
        return controlLable;
    }

    public void setControlLable(String controlLable) {
        this.controlLable = controlLable;
    }

    public String getControlDefault() {
        return controlDefault;
    }

    public void setControlDefault(String controlDefault) {
        this.controlDefault = controlDefault;
    }

    public String getControlType() {
        return controlType;
    }

    public void setControlType(String controlType) {
        this.controlType = controlType;
    }
}
