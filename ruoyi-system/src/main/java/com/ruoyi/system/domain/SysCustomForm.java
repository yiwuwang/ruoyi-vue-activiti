package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 自定义表单对象 sys_custom_form
 * 
 * @author KisChang
 * @date 2020-12-29
 */
public class SysCustomForm extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 表单名称 */
    @Excel(name = "表单名称")
    private String formName;

    /** 表单描述 */
    @Excel(name = "表单描述")
    private String descText;

    /** 表单Key */
    @Excel(name = "表单Key")
    private String formKey;

    /** 表单JSON */
    private String formJson;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setFormName(String formName) 
    {
        this.formName = formName;
    }

    public String getFormName() 
    {
        return formName;
    }
    public void setDescText(String descText) 
    {
        this.descText = descText;
    }

    public String getDescText() 
    {
        return descText;
    }
    public void setFormKey(String formKey) 
    {
        this.formKey = formKey;
    }

    public String getFormKey() 
    {
        return formKey;
    }
    public void setFormJson(String formJson) 
    {
        this.formJson = formJson;
    }

    public String getFormJson() 
    {
        return formJson;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("formName", getFormName())
            .append("descText", getDescText())
            .append("formKey", getFormKey())
            .append("formJson", getFormJson())
            .toString();
    }
}
