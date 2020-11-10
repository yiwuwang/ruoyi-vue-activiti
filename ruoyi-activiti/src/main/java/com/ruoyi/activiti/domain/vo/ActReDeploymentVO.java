package com.ruoyi.activiti.domain.vo;

import java.util.Date;

/**
 * 汇讯数码科技(深圳)有限公司
 * 创建日期:2020/10/23-15:42
 * 版本   开发者     日期
 * 1.0    Danny    2020/10/23
 */
public class ActReDeploymentVO {
    private String id;
    private Date deployTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Date getDeployTime() {
        return deployTime;
    }

    public void setDeployTime(Date deployTime) {
        this.deployTime = deployTime;
    }
}
