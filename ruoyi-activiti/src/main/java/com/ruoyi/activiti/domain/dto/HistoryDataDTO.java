package com.ruoyi.activiti.domain.dto;

import java.util.List;

public class HistoryDataDTO {
    private String taskNodeName;
    private String createName;
    private String createdDate;
    private List<HistoryFormDataDTO> formHistoryDataDTO;



    public String getTaskNodeName() {
        return taskNodeName;
    }

    public void setTaskNodeName(String taskNodeName) {
        this.taskNodeName = taskNodeName;
    }

    public String getCreateName() {
        return createName;
    }

    public void setCreateName(String createName) {
        this.createName = createName;
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
