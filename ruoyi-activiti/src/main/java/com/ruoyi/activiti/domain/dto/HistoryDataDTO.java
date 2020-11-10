package com.ruoyi.activiti.domain.dto;

import java.util.List;

/**
 * 汇讯数码科技(深圳)有限公司
 * 创建日期:2020/11/3-15:50
 * 版本   开发者     日期
 * 1.0    Danny    2020/11/3
 */
public class HistoryDataDTO {
    private String taskNodeName;
    private String createBy;
    private String createdDate;
    private List<HistoryFormDataDTO> formHistoryDataDTO;

    public String getTaskNodeName() {
        return taskNodeName;
    }

    public void setTaskNodeName(String taskNodeName) {
        this.taskNodeName = taskNodeName;
    }

    public String getCreateBy() {
        return createBy;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public List<HistoryFormDataDTO> getFormHistoryDataDTO() {
        return formHistoryDataDTO;
    }

    public void setFormHistoryDataDTO(List<HistoryFormDataDTO> formHistoryDataDTO) {
        this.formHistoryDataDTO = formHistoryDataDTO;
    }
}
