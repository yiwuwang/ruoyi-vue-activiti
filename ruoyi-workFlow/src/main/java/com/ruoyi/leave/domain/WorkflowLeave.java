package com.ruoyi.leave.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 请假对象 workflow_leave
 * 
 * @author danny
 * @date 2020-10-28
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class WorkflowLeave extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private String id;

    /** 请假类型 */
    @Excel(name = "请假类型")
    private String type;

    /** 标题 */
    @Excel(name = "标题")
    private String title;

    /** 原因 */
    @Excel(name = "原因")
    private String reason;

    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date leaveStartTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date leaveEndTime;


    private String instanceId;
    private String taskName;

    /** 状态 */
    @Excel(name = "状态")
    private String state;

    /** 创建人 */
    @Excel(name = "创建人")
    private String createName;

}
