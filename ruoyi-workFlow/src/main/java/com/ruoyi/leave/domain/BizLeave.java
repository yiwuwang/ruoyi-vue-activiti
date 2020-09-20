package com.ruoyi.leave.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.activiti.domain.ActivitiBaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;

/**
 * 请假流程对象 biz_leave
 * 
 * @author ruoyi
 * @date 2020-09-17
 */
public class BizLeave extends ActivitiBaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键ID */
    private Long id;

    /** 请假类型 */
    @Excel(name = "请假类型")
    private String type;



    /** 开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "开始时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date leaveStartTime;

    /** 结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "结束时间", width = 30, dateFormat = "yyyy-MM-dd HH:mm:ss")
    private Date leaveEndTime;

    /** 请假时长，单位秒 */
    @Excel(name = "请假时长，单位秒")
    private Long totalTime;


    /** 申请人 */
    @Excel(name = "申请人")
    private String applyUser;

    /** 申请时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "申请时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applyTime;

    /** 实际开始时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "实际开始时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date realityStartTime;

    /** 实际结束时间 */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Excel(name = "实际结束时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date realityEndTime;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setType(String type) 
    {
        this.type = type;
    }

    public String getType() 
    {
        return type;
    }

    public void setLeaveStartTime(Date leaveStartTime) 
    {
        this.leaveStartTime = leaveStartTime;
    }

    public Date getLeaveStartTime() 
    {
        return leaveStartTime;
    }
    public void setLeaveEndTime(Date leaveEndTime) 
    {
        this.leaveEndTime = leaveEndTime;
    }

    public Date getLeaveEndTime() 
    {
        return leaveEndTime;
    }
    public void setTotalTime(Long totalTime) 
    {
        this.totalTime = totalTime;
    }

    public Long getTotalTime()
    {
        return totalTime;
    }

    public void setApplyUser(String applyUser)
    {
        this.applyUser = applyUser;
    }

    public String getApplyUser() 
    {
        return applyUser;
    }
    public void setApplyTime(Date applyTime) 
    {
        this.applyTime = applyTime;
    }

    public Date getApplyTime() 
    {
        return applyTime;
    }
    public void setRealityStartTime(Date realityStartTime) 
    {
        this.realityStartTime = realityStartTime;
    }

    public Date getRealityStartTime() 
    {
        return realityStartTime;
    }
    public void setRealityEndTime(Date realityEndTime) 
    {
        this.realityEndTime = realityEndTime;
    }

    public Date getRealityEndTime() 
    {
        return realityEndTime;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("type", getType())
            .append("title", getTitle())
            .append("reason", getReason())
            .append("leaveStartTime", getLeaveStartTime())
            .append("leaveEndTime", getLeaveEndTime())
            .append("totalTime", getTotalTime())
            .append("instanceId", getInstanceId())
            .append("createBy", getCreateBy())
            .append("createTime", getCreateTime())
            .append("updateBy", getUpdateBy())
            .append("updateTime", getUpdateTime())
            .append("applyUser", getApplyUser())
            .append("applyTime", getApplyTime())
            .append("realityStartTime", getRealityStartTime())
            .append("realityEndTime", getRealityEndTime())
            .toString();
    }
}
