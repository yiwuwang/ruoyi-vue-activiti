package com.ruoyi.activiti.service;

import com.ruoyi.activiti.domain.dto.ActivitiHighLineDTO;

public interface IActivitiHistoryService {
    public ActivitiHighLineDTO gethighLine(String instanceId);
}
